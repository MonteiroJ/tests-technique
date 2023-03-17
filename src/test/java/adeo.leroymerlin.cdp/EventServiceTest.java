package adeo.leroymerlin.cdp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    public void testGetFilteredEvents() {
        // Create some sample data
        List<Member> members1 = Arrays.asList(Utils.getMember("John Doe"), Utils.getMember("Jane Smith"));
        List<Member> members2 = Arrays.asList(Utils.getMember("Alice Brown"), Utils.getMember("Bob Johnson"));
        List<Member> members3 = Arrays.asList(Utils.getMember("Carl Davis"), Utils.getMember("Debby Edwards"));
        Band band1 = Utils.getBand("Band 1", new HashSet<>(members1));
        Band band2 = Utils.getBand("Band 2", new HashSet<>(members2));
        Band band3 = Utils.getBand("Band 3", new HashSet<>(members3));
        Set<Band> bands1 = new HashSet<>(Arrays.asList(band1, band2));
        Set<Band> bands2 = new HashSet<>(Arrays.asList(band2, band3));
        Event event1 = Utils.getEvent("Event 1", "img/1.jpg", bands1);
        Event event2 = Utils.getEvent("Event 2", "img/2.jpg", bands2);
        List<Event> events = Arrays.asList(event1, event2);

        // Set up the mock repository to return the sample data
        Mockito.when(eventRepository.findAllBy()).thenReturn(events);

        // Test the getFilteredEvents method with the query "Smith"
        List<Event> filteredEvents = eventService.getFilteredEvents("Smith");

        // Assert that the correct events and bands were filtered
        assertEquals(1, filteredEvents.size());
        assertEquals("Event 1 [1]", filteredEvents.get(0).getTitle());
        Set<Band> filteredBands = filteredEvents.get(0).getBands();
        assertEquals(1, filteredBands.size());

        Band filteredBand = filteredBands.iterator().next();
        assertEquals("Band 1 [1]", filteredBand.getName());
        Set<Member> filteredMembers = filteredBand.getMembers();
        assertEquals(1, filteredMembers.size());

        Member filteredMember = filteredMembers.iterator().next();
        assertEquals("Jane Smith", filteredMember.getName());
    }
}
