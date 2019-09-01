
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Item;
import domain.Provider;

@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository	itemRepository;

	// Supporting services -----------------------------
	@Autowired
	private ProviderService	providerService;


	// Constructors ------------------------------------

	public ItemService() {
		super();
	}

	// Simple CRUDs methods ----------------------------

	public Item create() {
		Item res;
		Provider principal;

		principal = this.providerService.findByPrincipal();
		Assert.notNull(principal);

		res = new Item();
		res.setProvider(principal);

		return res;
	}

	public Item save(final Item item) {
		Assert.notNull(item);
		Item res;
		Provider principal;

		principal = this.providerService.findByPrincipal();
		Assert.notNull(principal);
		if (item.getId() != 0)
			Assert.isTrue(item.getProvider() == principal);
		res = this.itemRepository.save(item);

		return res;
	}

	public void delete(final Item item) {
		Provider principal;

		principal = this.providerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(item.getProvider() == principal);
		this.itemRepository.delete(item);
	}

	public Collection<Item> findAll() {
		return this.itemRepository.findAll();
	}

	public Item findOne(final int itemId) {
		Assert.notNull(itemId);

		final Item res = this.itemRepository.findOne(itemId);

		return res;
	}

	public void flush() {
		this.itemRepository.flush();
	}

	// Other business methods ---------------------------

	public Collection<Item> findItemsByProvider(final int providerId) {
		Collection<Item> res;

		res = this.itemRepository.findItemsByProvider(providerId);

		return res;
	}

	public void deleteByUserDropOut(final Item item) {
		this.itemRepository.delete(item.getId());

	}
}
