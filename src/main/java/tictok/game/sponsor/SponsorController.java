package main.java.tictok.game.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tictok.game.sponsor.Sponsor;
import tictok.game.sponsor.SponsorService;


@RestController
public class SponsorController {

    @Autowired
    private SponsorService sponsorservice;

    @GetMapping("/")
    public String home(){
        return "Hello Player" ;
    }


    //@PostMapping("/sponsor/name/{name}/description/{desc}")
    @PostMapping(value = "/sponsor")
    public String addSponsor(@RequestParam long id
            ,@RequestParam String name
            ,@RequestParam String desc
            , @RequestParam String address){

        Sponsor s = new Sponsor(id,name,desc,address);
        sponsorservice.addSponsor(s);





        return "Hello" + " " + name + " " + desc + " " + address ;
    }

}
