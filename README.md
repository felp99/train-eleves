# Railway System Simulation

## Overview
This Java project simulates a basic railway system. It models the movement of trains on railway tracks, including stations and track sections. The system demonstrates object-oriented principles and thread synchronization in Java.

## System Components

### Classes

- `Element` (Abstract Class)
  - Generic representation of an element in the railway circuit (either `Station` or `Section`).
- `Station`
  - Represents a station in the railway network with a certain capacity (number of platforms).
- `Section`
  - Represents a section of the railway track.
- `Railway`
  - Represents the entire railway circuit, composed of stations and track sections.
- `Position`
  - Represents the position of a train within the railway circuit, consisting of an `Element` and a `Direction`.
- `Train`
  - Represents a train, characterized by its name and its position in the railway circuit. Implements the `Runnable` interface for concurrent movement simulation.
- `Direction` (Enum)
  - Represents the direction in which a train can travel (from left to right or from right to left).

### Exceptions

- `BadPositionForTrainException`
  - Custom exception thrown when a train is assigned an invalid position.

## Usage

1. **Setup Railway Network:**
   - Instantiate `Station` and `Section` objects.
   - Create a `Railway` instance by passing an array of `Element` (stations and sections).
  
2. **Initialize Trains:**
   - Instantiate `Train` objects with a name, associated `Railway`, and initial `Position`.
   - Ensure the initial position is a station (`Station` instance), otherwise `BadPositionForTrainException` will be thrown.

3. **Start Simulation:**
   - For each `Train` object, create a `Thread` and start it.
   - Trains will move based on their current position and direction, using the `moveLeftToRight` and `moveRightToLeft` methods in `Railway`.

4. **Monitor and Control:**
   - The movement of trains can be monitored and logged using the `toString` methods in the `Train` and `Position` classes.
   - The simulation runs for a predefined number of iterations or until manually stopped.


