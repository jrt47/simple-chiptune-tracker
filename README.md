# A simple chiptune tracker

## Jonah Townsend


This application will allow users to make music using simple waveforms in an authentic tracker format, without the
additional complexities of professional music tracker software. The application is intended for anyone who wants to
make old-style chiptune music but has little experience using music trackers.

Users will be able to program up to four instrument channels, including:
- Two pulse waves
- A triangle wave
- A noise generator

After building a track, users will have the option for the application to play it back to them at a tempo of their
choice.

As someone who is interested in music as a hobby, this project is very compelling. I have been interested in using
old-style chiptune sounds in the past, but the technical complexity of modern-day tracking software has always been a
barrier. I am excited to start using the application when it is finished.

## User Stories

- As a user, I want to be able to create a new track
- As a user, I want to be able to set the name of my track
- As a user, I want to be able to view all the notes and rests placed in my track
- As a user, I want to be able to add a note or a rest to an instrument channel in my track
- As a user, I want to be able to add an effect to a note in my track
- As a user, I want to be able to clear a selection of notes from my track
- As a user, I want to be able to add or remove a number of bars from my track
- As a user, I want to be able to transpose my track or a channel in my track by a given number of semitones
- As a user, I want to be able to choose the tempo of my track
- As a user, I want to be able to save each of my tracks to file
- As a user, I want to be able to load each of my tracks from file
- As a user, I want to be able to delete each of my tracks from file

## Phase 4: Task 2

I chose to implement a type hierarchy in the sound package of the ui package. The classes NoiseOscillator,
PulseOscillator, and TriangleOscillator all have distinct functionality and extend the abstract class called
Oscillator.

## Phase 4: Task 3

If I had more time to work on the project, there are several things I could refactor to improve the design of my code:

- Increase cohesion by splitting the MainMenu class into two separate classes, one that represents the entire menu window
and another that represents the main menu card
- Remove unnecessary code by deleting the InstrumentChannel class and replacing it with a list of events in Track
- Remove the associations between TrackPlayer and Track / ToolBar and have TrackPlayer access these objects through the
TrackerApp
- Extract duplication from NewMenu and LoadMenu to an abstract MenuCard superclass
- Extract duplication from FileMenu, EditMenu, and HelpMenu to an abstract MenuBarMenu superclass
- Increase cohesion by making Event abstract and creating Note, Rest, and Blank classes that extend Event
- Create a ChannelName enum instead of using strings to represent channel names to improve readability
- Make Tracker implement Iterable instead of accessing the list of tracks in the tracker directly to iterate over