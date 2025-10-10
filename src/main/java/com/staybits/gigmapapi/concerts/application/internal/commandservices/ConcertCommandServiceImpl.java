package com.staybits.gigmapapi.concerts.application.internal.commandservices;

import com.staybits.gigmapapi.authentication.infrastructure.persistence.jpa.repositories.UserRepository;
import com.staybits.gigmapapi.concerts.domain.model.aggregates.Concert;
import com.staybits.gigmapapi.concerts.domain.model.commands.CreateConcertCommand;
import com.staybits.gigmapapi.concerts.domain.model.commands.DeleteConcertCommand;
import com.staybits.gigmapapi.concerts.domain.model.commands.UpdateConcertCommand;
import com.staybits.gigmapapi.concerts.domain.services.ConcertCommandService;
import com.staybits.gigmapapi.concerts.infrastructure.persistence.jpa.repositories.ConcertRepository;
import org.springframework.stereotype.Service;

/**
 * Concert command service implementation.
 * <p>
 *     This class implements the service to handle concert commands.
 * </p>
 */
@Service
public class ConcertCommandServiceImpl implements ConcertCommandService {

    private final ConcertRepository concertRepository;
    private final UserRepository userRepository;

    public ConcertCommandServiceImpl(ConcertRepository concertRepository, UserRepository userRepository) {
        this.concertRepository = concertRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Concert handle(CreateConcertCommand command) {
        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("User with id %s not found".formatted(command.userId())));

        var concert = new Concert(
                command.title(),
                command.datehour(),
                command.description(),
                command.imageUrl(),
                command.venue(),
                command.status(),
                user,
                command.genre(),
                command.platform()
        );

        // Validate that the user is an artist
        if (!concert.isValidArtist()) {
            throw new IllegalArgumentException("User must have ARTIST role to create a concert");
        }

        return concertRepository.save(concert);
    }

    @Override
    public Concert handle(UpdateConcertCommand command) {
        var existingConcert = concertRepository.findById(command.id());
        
        if (existingConcert.isEmpty()) {
            return null;
        }

        var concert = existingConcert.get();
        concert.setTitle(command.title());
        concert.setDatehour(command.datehour());
        concert.setDescription(command.description());
        concert.setImageUrl(command.imageUrl());
        concert.setVenue(command.venue());
        concert.setStatus(command.status());
        concert.setGenre(command.genre());
        concert.setPlatform(command.platform());

        return concertRepository.save(concert);
    }

    @Override
    public boolean handle(DeleteConcertCommand command) {
        var concert = concertRepository.findById(command.id());
        
        if (concert.isEmpty()) {
            return false;
        }

        concertRepository.delete(concert.get());
        return true;
    }
}
