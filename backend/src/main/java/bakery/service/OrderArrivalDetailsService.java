package bakery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bakery.model.TblOrderArrivalDetails;
import bakery.repository.OrderArrivalDetailsRepository;

@Service
public class OrderArrivalDetailsService {
	
	@Autowired
	private OrderArrivalDetailsRepository orderArrivalDetailsRepo;
	
	public List<TblOrderArrivalDetails> getAllArrivalDetails(){
		return orderArrivalDetailsRepo.findAll();
	}
	
	public TblOrderArrivalDetails getOrderArrivalDetailsById(Long arrivalDetailsId) {
		return orderArrivalDetailsRepo.findById(arrivalDetailsId).get();
	}
	
	public TblOrderArrivalDetails addArrivalDetails(TblOrderArrivalDetails arrivalDetails) {
		return orderArrivalDetailsRepo.save(arrivalDetails);
	}
	
	public void deleteArrivalDetailsById(Long arrivalDetailsId) {
		orderArrivalDetailsRepo.deleteById(arrivalDetailsId);
	}
	
	public TblOrderArrivalDetails updateArrivalDetails(Long arrivalDetailsId, TblOrderArrivalDetails arrivalDetails) {
		TblOrderArrivalDetails oldArrivalDetails = orderArrivalDetailsRepo.findById(arrivalDetailsId).get();
		
		if(oldArrivalDetails == null) {
			return null;
		}
		
		oldArrivalDetails.setArrivalDate(arrivalDetails.getArrivalDate());
		oldArrivalDetails.setArrivalTime(arrivalDetails.getArrivalTime());
		oldArrivalDetails.setCountry(arrivalDetails.getCountry());
		oldArrivalDetails.setPostalCode(arrivalDetails.getPostalCode());
		oldArrivalDetails.setCity(arrivalDetails.getCity());
		oldArrivalDetails.setStreetName(arrivalDetails.getStreetName());
		oldArrivalDetails.setStreetNumber(arrivalDetails.getStreetNumber());
		
		return orderArrivalDetailsRepo.save(oldArrivalDetails);
	}

}
