package esi.buildit9.rest.controller;


import esi.buildit9.domain.*;
import esi.buildit9.rest.PurchaseOrderAssembler;
import esi.buildit9.rest.PurchaseOrderLineResource;
import esi.buildit9.rest.PurchaseOrderResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class PurchaseOrderRestController {

    private PurchaseOrderAssembler assembler;

    public PurchaseOrderRestController() {
        assembler = new PurchaseOrderAssembler();
    }

    @RequestMapping(value = "pos", method = RequestMethod.POST)
    public ResponseEntity<Void> createOrder(@RequestBody PurchaseOrderResource res) {
        PurchaseOrder order= new PurchaseOrderAssembler().fromResource(res);
        order.setOrderStatus(OrderStatus.CREATED);

        order.persist();

		attachLines(order, res.getPurchaseOrderLines());

		HttpHeaders headers = new HttpHeaders();
		URI location =
				ServletUriComponentsBuilder.fromCurrentRequestUri().
						pathSegment(order.getId().toString()).build().toUri();
		headers.setLocation(location);
        headers.add("BuildItId", order.getId().toString());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping("po/{id}")
    public ResponseEntity<PurchaseOrderResource> getById(@PathVariable Long id) {
        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(id);
        PurchaseOrderResource resources = assembler.toResource(order);
        return new ResponseEntity<PurchaseOrderResource>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "po/{id}/modify", method = RequestMethod.PUT)
    public ResponseEntity<Void> modifyOrder(@PathVariable Long id, @RequestBody PurchaseOrderResource res) {
        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(id);
        order.setRentit(RentIt.getOrCreateRentIt(res.getRentit()));
        order.setOrderStatus(res.getOrderStatus());
        order.setSite(Site.getOrCreateSite(res.getSiteAddress()));
        order.setSiteEngineerName(res.getSiteEngineerName());
        order.setTotalPrice(res.getTotalPrice());
        order.setWorksEngineerName(res.getWorksEngineerName());
        order.persist();

        deleteLines(order);

        attachLines(order, res.getPurchaseOrderLines());

        HttpHeaders headers = new HttpHeaders();
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequestUri().
                        pathSegment(order.getId().toString()).build().toUri();
        headers.setLocation(location);
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

    private void deleteLines(PurchaseOrder order) {
        for (PurchaseOrderLine line : order.getLines()){
            line.remove();
        }
    }

    private void attachLines(PurchaseOrder order, List<PurchaseOrderLineResource> purchaseOrderLines) {
		for (PurchaseOrderLineResource res : purchaseOrderLines) {
			attachLine(order, res);
		}
	}

	private void attachLine(PurchaseOrder order, PurchaseOrderLineResource res) {
		PurchaseOrderLine line = new PurchaseOrderLine();
		line.setPlantExternalId(res.getPlantId());
        line.setPlantName(res.getPlantName());
		line.setStartDate(res.getStartDate());
		line.setEndDate(res.getEndDate());
		line.setTotalCost(res.getTotalCost()); // TODO: recalculate
		line.setPurchaseOrder(order);
		line.persist();
	}

}
