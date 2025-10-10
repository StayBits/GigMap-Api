package com.staybits.gigmapapi.concerts.interfaces.rest;

import com.staybits.gigmapapi.concerts.domain.model.commands.DeleteConcertCommand;
import com.staybits.gigmapapi.concerts.domain.model.queries.GetAllConcertsQuery;
import com.staybits.gigmapapi.concerts.domain.model.queries.GetConcertByIdQuery;
import com.staybits.gigmapapi.concerts.domain.services.ConcertCommandService;
import com.staybits.gigmapapi.concerts.domain.services.ConcertQueryService;
import com.staybits.gigmapapi.concerts.interfaces.rest.resources.ConcertResource;
import com.staybits.gigmapapi.concerts.interfaces.rest.resources.CreateConcertResource;
import com.staybits.gigmapapi.concerts.interfaces.rest.resources.UpdateConcertResource;
import com.staybits.gigmapapi.concerts.interfaces.rest.transform.ConcertResourceFromEntityAssembler;
import com.staybits.gigmapapi.concerts.interfaces.rest.transform.CreateConcertCommandFromResourceAssembler;
import com.staybits.gigmapapi.concerts.interfaces.rest.transform.UpdateConcertCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/concerts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Concerts", description = "Operations related to Concerts")
public class ConcertsController {
    
    private final ConcertCommandService concertCommandService;
    private final ConcertQueryService concertQueryService;

    public ConcertsController(ConcertCommandService concertCommandService, ConcertQueryService concertQueryService) {
        this.concertCommandService = concertCommandService;
        this.concertQueryService = concertQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new concert", description = "Creates a new concert with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Concert created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ConcertResource> createConcert(@RequestBody CreateConcertResource resource) {
        var command = CreateConcertCommandFromResourceAssembler.toCommandFromResource(resource);
        var concert = concertCommandService.handle(command);
        
        if (concert == null || concert.getId() == null || concert.getId() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        
        var concertResponse = ConcertResourceFromEntityAssembler.toResourceFromEntity(concert);
        return new ResponseEntity<>(concertResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all concerts", description = "Retrieve all concerts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concerts found"),
            @ApiResponse(responseCode = "404", description = "No concerts found")
    })
    public ResponseEntity<List<ConcertResource>> getAllConcerts() {
        var concerts = concertQueryService.handle(new GetAllConcertsQuery());
        
        if (concerts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var concertResources = ConcertResourceFromEntityAssembler.toResourcesFromEntities(concerts);
        return ResponseEntity.ok(concertResources);
    }

    @GetMapping("/{concertId}")
    @Operation(summary = "Get concert by ID", description = "Retrieve a concert by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concert found"),
            @ApiResponse(responseCode = "404", description = "Concert not found")
    })
    public ResponseEntity<ConcertResource> getConcertById(@PathVariable Long concertId) {
        var concert = concertQueryService.handle(new GetConcertByIdQuery(concertId));
        
        if (concert.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var concertResource = ConcertResourceFromEntityAssembler.toResourceFromEntity(concert.get());
        return ResponseEntity.ok(concertResource);
    }

    @PutMapping("/{concertId}")
    @Operation(summary = "Update concert", description = "Update an existing concert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concert updated successfully"),
            @ApiResponse(responseCode = "404", description = "Concert not found")
    })
    public ResponseEntity<ConcertResource> updateConcert(@PathVariable Long concertId, @RequestBody UpdateConcertResource resource) {
        var command = UpdateConcertCommandFromResourceAssembler.toCommandFromResource(resource);
        var concert = concertCommandService.handle(command);
        
        if (concert == null) {
            return ResponseEntity.notFound().build();
        }
        
        var concertResource = ConcertResourceFromEntityAssembler.toResourceFromEntity(concert);
        return ResponseEntity.ok(concertResource);
    }

    @DeleteMapping("/{concertId}")
    @Operation(summary = "Delete concert", description = "Delete a concert by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concert deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Concert not found")
    })
    public ResponseEntity<String> deleteConcert(@PathVariable Long concertId) {
        var command = new DeleteConcertCommand(concertId);
        boolean deleted = concertCommandService.handle(command);
        
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok("Concert with given id successfully deleted");
    }

    @GetMapping("/{concertId}/attendees")
    @Operation(summary = "Check if user is attending the concert", description = "Returns true if the given user is attending the specified concert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User attendance status retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Concert not found")
    })
    public ResponseEntity<Boolean> isUserAttendingConcert(@PathVariable Long concertId, @RequestParam Long userId) {
        Boolean isAttending = concertQueryService.checkAttendance(concertId, userId);
        return ResponseEntity.ok(isAttending);
    }
}
