package com.zhlian.module_bdvoice.constant;

/**
 * Created by Hua on 2018-11-22.
 * Power By ZHLian
 */

public enum NLPDomainEnum {
    WEATHER("weather"),CALENDAR("calendar"),TRAIN("train"),FLIGHT("flight"),MAP("map"),
    TELEPHONE("telephone"),CONTACT("contact"),MESSAGE("message"),APP("app"),WEBSITE("website"),
    ALARM("alarm"),SNS("sns"),SETTING("setting"),MUSIC("music"),JOKE("joke"),STORY("story"),
    HOTEL("hotel"),TRAVEL("travel"),INSTRUCTION("instruction"),VIDEO("video"),TRANSLATION("translation"),
    PHONE_CHARGES("phone_charges"),TV_SHOW("tv_show"),PERSON("person"),TV_INSTRUCTION("tv_instruction"),
    STOCK("stock"),NOVEL("novel"),PLAYER("player"),ACCOUNT("account"),SEARCH("search"),VEHICLE_INSTRUCTION("vehicle_instruction"),
    RADIO("radio"),RECIPE("recipe"),NAVIGATE_INSTRUCTION("navigate_instruction"),MOVIE_NEWS("movie_news");

    private String domain;

    NLPDomainEnum(String domain){
        this.domain = domain;
    }

    public String getDomain(){
        return domain;
    }
}
