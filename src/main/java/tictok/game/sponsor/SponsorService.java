package tictok.game.sponsor;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import org.springframework.stereotype.Service;
import com.google.appengine.api.datastore.Entity;
import tictok.game.sponsor.Sponsor;

@Service
public class SponsorService {
    public void addSponsor(Sponsor s) {

//        Entity sp = new Entity("Sponsor", s.getId());
//        sp.setProperty("firstName",s.getName());
//        sp.setProperty("description",s.getDescription());
//        sp.setProperty("address",s.getAddress());
//
//        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//        datastore.put(sp);
        System.out.println(s.getId());
        System.out.println("Sponsor Loaded");

    }
}
