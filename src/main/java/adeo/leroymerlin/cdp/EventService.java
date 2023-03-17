package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.delete(id);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here

        return events.stream()
                .filter(event -> event.getBands().stream()
                        .anyMatch(band -> band.getMembers().stream()
                                .anyMatch(member -> member.getName().contains(query))))
                // Filter events where any of their bands have members matching the query
                .peek(event -> {

                    List<Band> filteredBands = event.getBands().stream()
                            .filter(band -> band.getMembers().stream()
                                    .anyMatch(member -> member.getName().contains(query)))
                            // Filter bands where any of their members match the query
                            .peek(band -> {
                                List<Member> members = band.getMembers().stream()
                                        .filter(member -> member.getName().contains(query))
                                        .collect(Collectors.toList()); // Filter members matching the query and collect them in a list
                                band.setMembers(new HashSet<>(members));
                            })
                            .collect(Collectors.toList()); // Collect the filtered bands in a list

                    event.setBands(new HashSet<>(filteredBands)); // Set the filtered bands on the event, converting the list to a set
                })
                .collect(Collectors.toList()); // Collect the filtered events in a list
    }
}
