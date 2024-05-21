package bakery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bakery.model.TblOrderArrivalDetails;
import bakery.service.OrderArrivalDetailsService;

@RestController
@RequestMapping("/orderArrivalDetails")
public class OrderArrivalDetailsController {
	
	@Autowired
	private OrderArrivalDetailsService arrivalDetailsService;
	
	@GetMapping("")
	public ResponseEntity<List<TblOrderArrivalDetails>> getAllOrderArrivalDetails(){
		List<TblOrderArrivalDetails> arrivalDetails = arrivalDetailsService.getAllArrivalDetails();
		
		return new ResponseEntity<>(arrivalDetails, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TblOrderArrivalDetails> getArrivalDetailsById(@PathVariable Long id){
		TblOrderArrivalDetails arrivalDetails = arrivalDetailsService.getOrderArrivalDetailsById(id);
		if(arrivalDetails == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(arrivalDetails, HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<TblOrderArrivalDetails> addArrivalDetails(@RequestBody TblOrderArrivalDetails arrivalDetails){
		TblOrderArrivalDetails newArrivalDetails = arrivalDetailsService.addArrivalDetails(arrivalDetails);
		return new ResponseEntity<>(newArrivalDetails, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TblOrderArrivalDetails> updateArrivalDetails(@PathVariable Long id, @RequestBody TblOrderArrivalDetails arrivalDetails){
		TblOrderArrivalDetails updatedArrivalDetails = arrivalDetailsService.updateArrivalDetails(id, arrivalDetails);
		if(updatedArrivalDetails == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(updatedArrivalDetails, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteArrivalDetails(@PathVariable Long id){
		arrivalDetailsService.deleteArrivalDetailsById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
