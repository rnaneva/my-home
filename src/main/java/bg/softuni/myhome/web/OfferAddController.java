package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.OfferPageOneDTO;
import bg.softuni.myhome.model.dto.OfferPageThreeDTO;
import bg.softuni.myhome.model.dto.OfferPageTwoDTO;
import bg.softuni.myhome.model.entities.OfferPageOne;
import bg.softuni.myhome.model.entities.OfferPageTwo;
import bg.softuni.myhome.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.NoPermissionException;

import java.util.List;

import static bg.softuni.myhome.commons.StaticVariables.BINDING_RESULT;

@Controller
@RequestMapping("/agency/offers")
public class OfferAddController {

    private final static String REDIRECT_PAGE_TWO = "redirect:/agency/offers/add/two/";
    private final static String REDIRECT_PAGE_THREE = "redirect:/agency/offers/add/three/";
    private final static String REDIRECT_PAGE_ONE = "redirect:/agency/offers/add/one/";


    private OfferPageOneService offerPageOneService;
    private OfferService offerService;
    private CategoryService categoryService;
    private CityService cityService;
    private OfferPageTwoService offerPageTwoService;
    private PictureService pictureService;

    public OfferAddController(OfferPageOneService offerPageOneService, OfferService offerService,
                              CategoryService categoryService, CityService cityService,
                              OfferPageTwoService offerPageTwoService, PictureService pictureService) {
        this.offerPageOneService = offerPageOneService;
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.offerPageTwoService = offerPageTwoService;

        this.pictureService = pictureService;
    }


    @GetMapping("/add/one/{id}")
    public String getAddOfferPageOne(@PathVariable("id") String userVisibleId,
                                     Model model,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        authorize(userVisibleId, appUserDetails);

        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        model.addAttribute("categories", allCategoryNames);


        return "add-offer-one";
    }

    @PostMapping("/add/one/{id}")
    public String postAddOfferPageOne(@PathVariable("id") String userVisibleId,
                                      @Valid OfferPageOneDTO offerPageOneDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) throws NoPermissionException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddPageOneDTO", offerPageOneDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerPageOneDTO", bindingResult);


            return REDIRECT_PAGE_ONE + userVisibleId;

        }

        OfferPageOne pageOne = offerPageOneService.saveOfferPageOne(offerPageOneDTO);
        String offerId = offerService.createOfferWithPageOne(pageOne, userVisibleId).getVisibleId();


        return REDIRECT_PAGE_TWO + offerId;
    }


    @GetMapping("/add/two/{offerId}")
    public String getAddOfferPageTwo(@PathVariable("offerId") String offerVisibleId,
                                     Model model,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        List<String> allCityNames = cityService.getAllCityNames();
        model.addAttribute("cities", allCityNames);
        model.addAttribute("offerVisibleId", offerVisibleId);


        return "add-offer-two";
    }

    @PostMapping("/add/two/{offerId}")
    public String postAddOfferPageTwo(@PathVariable("offerId") String offerVisibleId,
                                      @Valid OfferPageTwoDTO offerPageTwoDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddPageTwoDTO", offerPageTwoDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerPageTwoDTO", bindingResult);


            return REDIRECT_PAGE_TWO + offerVisibleId;

        }


        OfferPageTwo pageTwo =
                offerPageTwoService.savePageTwo(offerPageTwoDTO);
        offerService.addPageTwoToOffer(pageTwo, offerVisibleId);

        return REDIRECT_PAGE_THREE + offerVisibleId;
    }


    @GetMapping("/add/three/{offerId}")
    public String getAddOfferPage3(@PathVariable("offerId") String offerVisibleId,
                                   Model model
                                  ) throws NoPermissionException {

        model.addAttribute("offerVisibleId", offerVisibleId);

        return "add-offer-three";
    }

    @PostMapping("/add/three/{offerId}")
    public String postAddOfferPageThree(@PathVariable("offerId") String offerVisibleId,
                                        @Valid OfferPageThreeDTO offerPageThreeDTO,
                                        @AuthenticationPrincipal AppUserDetails appUserDetails) {

         pictureService.savePictures(offerPageThreeDTO, offerVisibleId);

        return REDIRECT_PAGE_THREE + offerVisibleId;
    }


    private static void authorize(String userVisibleId, AppUserDetails appUserDetails) throws NoPermissionException {
        if (!appUserDetails.getVisibleId().equals(userVisibleId)) {
            throw new NoPermissionException();
        }
    }

    @ModelAttribute
    public OfferPageOneDTO offerAddPageOneDTO() {
        return new OfferPageOneDTO();
    }

    @ModelAttribute
    public OfferPageTwoDTO offerAddPageTwoDTO() {
        return new OfferPageTwoDTO();
    }

    @ModelAttribute
    public OfferPageThreeDTO offerAddPageThreeDTO(){
        return new OfferPageThreeDTO();
    }
}
