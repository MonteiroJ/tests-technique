package adeo.leroymerlin.cdp;

import java.util.Set;

public class Utils {

    public static Member getMember(String name) {
        Member member = new Member();
        member.setName(name);

        return member;
    }

    public static Band getBand(String name, Set<Member> members) {
        Band band = new Band();
        band.setName(name);
        band.setMembers(members);

        return band;
    }

    public static Event getEvent(String title, String imgUrl, Set<Band> bands) {
        Event event = new Event();
        event.setTitle(title);
        event.setImgUrl(imgUrl);
        event.setBands(bands);

        return event;
    }
}
