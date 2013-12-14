//package esi.buildit9.interop.rentit30;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
//
//import ee.ut.controller.PurchaseOrderRESTController;
//import ee.ut.domain.PurchaseOrder;
//
//public class PurchaseOrderResourceAssembler
//	extends ResourceAssemblerSupport<PurchaseOrder, PurchaseOrderResource>{
//
//	public PurchaseOrderResourceAssembler () {
//		 super(PurchaseOrderRESTController.class,PurchaseOrderResource.class);
//	}
//
//	public PurchaseOrderResourceList toResource(final List<PurchaseOrder> plist){
//
//		PurchaseOrderResourceList res = new PurchaseOrderResourceList();
//		List<PurchaseOrderResource> plants = new LinkedList<PurchaseOrderResource>();
//		for (int i = 0; i < plist.size(); i++){
//			plants.add(toResource(plist.get(i)));
//		}
//		res.setOrders(plants);
//		return res;
//	}
//
//	@Override
//	public PurchaseOrderResource toResource(PurchaseOrder p) {
//		PurchaseOrderResource res = createResourceWithId(p.getId(), p);
//		PlantResourceAssembler passembler = new PlantResourceAssembler();
//		res.setPoId(p.getId());
//		res.setSiteId(p.getSiteId());
//		res.setPrice(p.getCost());
//		res.setStatus(p.getStatus());
//		res.setStart(p.getStartDate());
//		res.setEnd(p.getEndDate());
//		res.setHireRequestId(p.getHireRequestId());
//		res.setPlant(passembler.toResource(p.getPlant()));
//		res.setEmail(p.getEmail());
//		res.setGetlink(p.getGetlink());
//		res.setCredentials(p.getCredentials());
//		return res;
//	}
//}
