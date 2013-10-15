package esi.buildit9.rest.controller;


import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.PurchaseOrderLine;
import esi.buildit9.rest.PurchaseOrderAssembler;
import esi.buildit9.rest.PurchaseOrderLineResource;
import esi.buildit9.rest.PurchaseOrderResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class PurchaseOrderRestController {

    @RequestMapping(value = "pos", method = RequestMethod.POST)
    public ResponseEntity<Void> createOrder(@RequestBody PurchaseOrderResource res) {
        PurchaseOrder order= new PurchaseOrderAssembler().fromResource(res);

        order.persist();

		attachLines(order, res.getPurchaseOrderLines());

		HttpHeaders headers = new HttpHeaders();
		URI location =
				ServletUriComponentsBuilder.fromCurrentRequestUri().
						pathSegment(order.getId().toString()).build().toUri();
		headers.setLocation(location);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    private void attachLines(PurchaseOrder order, List<PurchaseOrderLineResource> purchaseOrderLines) {
		for (PurchaseOrderLineResource res : purchaseOrderLines) {
			attachLine(order, res);
		}
	}

	private void attachLine(PurchaseOrder order, PurchaseOrderLineResource res) {
		PurchaseOrderLine line = new PurchaseOrderLine();
		line.setPlantExternalId(res.getPlantId());
		line.setStartDate(res.getStartDate());
		line.setEndDate(res.getEndDate());
		line.setTotalCost(res.getTotalCost()); // TODO: recalculate
		line.setPurchaseOrder(order);
		line.persist();
	}

}
