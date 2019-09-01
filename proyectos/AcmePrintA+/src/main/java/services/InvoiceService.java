package services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import repositories.InvoiceRepository;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import domain.Application;
import domain.Configuration;
import domain.Customer;
import domain.Invoice;

@Service
@Transactional
public class InvoiceService {

	// Managed repository ------------------------------------------------------
	@Autowired
	private InvoiceRepository invoiceRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private FileService fileService;

	// Simple CRUD methods -----------------------------------------------------

	public Invoice create(final int applicationId) {
		Assert.notNull(applicationId);
		Application a;
		Invoice res;
		Customer principal;

		principal = this.customerService.findByPrincipal();

		a = this.applicationService.findOne(applicationId);
		Assert.isTrue(principal.equals(a.getOrder().getCustomer()));

		res = new Invoice();
		res.setApplication(a);
		res.setMoment(new Date(System.currentTimeMillis() - 1));
		res.setPrice(this.calculatePrice(a));

		return res;
	}
	
	public domain.File generatePdf(Invoice res , Configuration config){
		/* 
		 * GENERACi�N DE PDF USANDO ITEXT
		 */
		
		//Usaremos el formato de 2 decimales para mostrar los costes
		DecimalFormat df = new DecimalFormat("#.00");
		
        //Creamos un objeto calendario para calcular cuando caduca la factura
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(res.getMoment());
        calendar.add(Calendar.DAY_OF_YEAR, 60); //La caducidad de una factura es de m�ximo 60 dias como maximo por ley

        //Creamos una variable para calcular los impuestos
        Double d = config.getVat() * res.getApplication().getOfferedPrice(); 
       
		domain.File resultFile = null;
		File file = new File("invoice.pdf");

		
		
		try {
			FileOutputStream baos = new FileOutputStream(file); 
			//Usamos PDFReader para la lectura de la plantilla y PDFStamper para la creaci�n de la factura
            PdfReader pdfReader = new PdfReader(ResourceUtils.getURL("classpath:pdfs/FACTURA.pdf"));
            PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);
            
            //A�adimos a los par�metros de la plantilla los valores de la factura
            pdfStamper.getAcroFields().setField("fecha",  new SimpleDateFormat("dd-MM-yyyy").format(res.getMoment()));
            pdfStamper.getAcroFields().setField("numero_factura", Integer.toString(res.getId()));
            pdfStamper.getAcroFields().setField("nombre_sistema", config.getSystemName());
            pdfStamper.getAcroFields().setField("nombre_usuario", res.getApplication().getOrder().getCustomer().getName() + " " + res.getApplication().getOrder().getCustomer().getSurname());
            pdfStamper.getAcroFields().setField("email_usuario", res.getApplication().getOrder().getCustomer().getEmail());
            pdfStamper.getAcroFields().setField("direccion_usuario", res.getApplication().getOrder().getCustomer().getAddress());
            pdfStamper.getAcroFields().setField("nombre_empresa", res.getApplication().getCompany().getName());
            pdfStamper.getAcroFields().setField("trabajo", "Impresi�n 3D");
            pdfStamper.getAcroFields().setField("metodo_pago", "Efectivo");
            pdfStamper.getAcroFields().setField("fecha_vencimiento", new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime()));
            pdfStamper.getAcroFields().setField("Cantidad", "	1"); //Generamos una factura por cada objeto impreso
            pdfStamper.getAcroFields().setField("descripcion", res.getApplication().getOrder().getStl()); 
            pdfStamper.getAcroFields().setField("precio_unitario", res.getApplication().getOfferedPrice().toString());
            pdfStamper.getAcroFields().setField("total", res.getApplication().getOfferedPrice().toString());
            pdfStamper.getAcroFields().setField("producto", res.getApplication().getOrder().getMaterial());
            pdfStamper.getAcroFields().setField("descripcion_pedido", "objeto 3D");
            pdfStamper.getAcroFields().setField("importe_total", res.getApplication().getOfferedPrice().toString());
            pdfStamper.getAcroFields().setField("importe_sin_impuestos",  df.format(res.getApplication().getOfferedPrice()));
            pdfStamper.getAcroFields().setField("impuesto", df.format(d) + " (" + config.getVat() * 100 + "%)");
            pdfStamper.getAcroFields().setField("total_con_impuestos", df.format(res.getPrice()));

            //Creamos una imagen con el logotipo de nuestra empresa
    		Image img = Image.getInstance(config.getBanner());
    		//Se�alamos donde va a estar ubicada la imagen (x, y)
            float x = 400;
            float y = 750;
            img.setAbsolutePosition(x, y);
            //Ajustamos el tama�o de la imagen
            img.scaleToFit(175, 75);
            //Usamos getOverContent para que aparezca delante del texto y no detr�s
            pdfStamper.getOverContent(1).addImage(img);
            
			pdfStamper.close(); 
            pdfReader.close();
            //Creamos la salida
			resultFile = this.fileService.createForInvoice(res.getId());

			byte[] bytes = readFileToByteArray(file);
			 
			resultFile.setData(bytes);
			resultFile.setUploadDate(new Date(System.currentTimeMillis()-1));
			resultFile.setInvoice(res);
			resultFile.setName("invoice" + res.getId() + ".pdf");
			
			this.fileService.save(resultFile);
			
            //Cerramos PDFStamper y PDFReader

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
        
		
		return resultFile;
	}
	
    private static byte[] readFileToByteArray(File file){
        FileInputStream fis = null;
        // Creating a byte array using the length of the file
        // file.length returns long which is cast to int
        byte[] bArray = new byte[(int) file.length()];
        try{
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();        
            
        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return bArray;
    }
	
	public Invoice save(final Invoice invoice) {
		Invoice res;
		Assert.notNull(invoice);

		res = this.invoiceRepository.save(invoice);
		
		this.generatePdf(res, this.configurationService.findConfiguration());
		
		return res;
	}

	public Collection<Invoice> findAll() {
		Collection<Invoice> result;

		result = this.invoiceRepository.findAll();

		return result;

	}

	public Invoice findOne(final int invoiceId) {
		Invoice result;

		result = this.invoiceRepository.findOne(invoiceId);

		return result;
	}

	public void flush() {
		this.invoiceRepository.flush();
	}

	// Other business methods ---------------------------

	public Invoice findInvoiceByApplicationId(final int applicationId) {
		Invoice res;

		res = this.invoiceRepository.findInvoiceByApplicationId(applicationId);

		return res;
	}

	public Invoice findInvoiceByOrderId(final int orderId) {
		Invoice res;

		res = this.invoiceRepository.findInvoiceByOrderId(orderId);

		return res;
	}

	public Collection<Invoice> findInvoicesByCompanyId(final int companyId) {
		Collection<Invoice> res;

		res = this.invoiceRepository.findInvoicesByCompanyId(companyId);

		return res;
	}

	public Collection<Invoice> findInvoicesByCustomerId(final int customerId) {
		Collection<Invoice> res;

		res = this.invoiceRepository.findInvoicesByCustomerId(customerId);

		return res;
	}

	private Double calculatePrice(final Application a) {
		Double res;
		Configuration c;

		c = this.configurationService.findConfiguration();
		res = a.getOfferedPrice() + c.getVat() * a.getOfferedPrice();

		return res;
	}
	
	public void deleteAnonymous(Invoice invoice){
		Assert.notNull(invoice);
		this.invoiceRepository.delete(invoice);
	}
	
	
	
	
	
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File("java.pdf");
 
        FileInputStream fis = new FileInputStream(file);
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

        
        byte[] bytes = bos.toByteArray();
 
        //below is the different part
        File someFile = new File("java2.pdf");
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
}
