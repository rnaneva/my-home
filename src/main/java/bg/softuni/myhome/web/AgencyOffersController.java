package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/agency/offers")
public class AgencyOffersController {


    @GetMapping("/active/{id}")
    public String getActiveOffers(@PathVariable("id") String userVisibleId,
                                  @AuthenticationPrincipal AppUserDetails appUserDetails){

        return "agency-offers";

    }


    @GetMapping("/inactive/{id}")
    public String getInactiveOffers(@PathVariable("id") String userVisibleId,
                                    @AuthenticationPrincipal AppUserDetails appUserDetails){

        return "agency-offers";

    }




}
