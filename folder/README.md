# Robot-sensor-car-simulation
This is an software simulations of servo motor light sensor(RI) Shield-bot with Automatic distance indication;

The Shield-bot used infra-red (IR) as an input to control the servo motors, it has infra-red (IR) LED as a "headlight" 
and pair it with an IR receiver to detect the light being reflected off walls and objects. 
Similar to the visible light sensor, as a wall moves closer, the IR receiver will detect more reflected IR light.
In this software simulation, the shield-bot equiped with front, left and right sensors, there are maze wall which meant for shield-bot to navigate. 

Press enter to run sheild bot automatically or use the arrow sign on the keyboard ← → to turn left and right ↑ to move forward/increase speed ↓ to decrease speed.

New mazes are available under walls.md, thinner gaps between walls, wider walls, curved walls

note: 
1. Open the terminal: ⌘ + SPACE for Spotlight > terminal > return.

2. If you don't have it already, install Homebrew package manager. It is a staple for installing apps with the terminal on Mac, so it's worth getting.

/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"Links to an external site.

Previous versions of these lab notes used: ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"Links to an external site.

3. Install SDL2 and SDL2 Graphics. You can find out which packages you want with:

  brew search sdl2

I'm getting:

sdl2        sdl2_image    sdl2_net    sdl

sdl2_gfx    sdl2_mixer    sdl2_ttf

I hear that these are all quite useful libraries, but for this project let's install sdl2 and sdl2_gfx:

  brew install sdl2

  brew install sdl2_gfx

Now, if you type brew search sdl2, there should be ticks next to sdl2 and sdl2_gfx:

sdl2 ✔      sdl2_image    sdl2_net    sdl

sdl2_gfx ✔  sdl2_mixer    sdl2_ttf

4. Install gcc compiler.

    brew install gcc

5. To avoid any "include" errors in an IDE like VSCode, etc, because it doesn't recognise SDL.h, put the following environment variables into your shell environment: on Mac usually ~/.zshrc, but if you use Bash it will be ~/.bash_profile.

# Use this section for Apple silicon Macs (M1 and M2 chips both share the same ARM64 architecture):
    export CPATH=/opt/homebrew/include
    export LIBRARY_PATH=/opt/homebrew/lib

# Use this section for Intel Macs:
    export CPATH=/usr/local/include
    export LIBRARY_PATH=/usr/local/lib

6. For adding the CPATH and LIBRARY_PATH variables, the only caveat is that, within the code, you will have to replace all instances (main.c, robot.h, and wall.h) of

#include "sdl.h"

// and

#include "SDL2_gfx-1.0.1/SDL2_gfxPrimitives.h"          Note: The 2021 Robot Template code contained simply "#include "SDL2_gfxPrimitives.h"

with

#include "SDL2/SDL.h"

// and

#include "SDL2/SDL2_gfxPrimitives.h"

You can quickly replace all instances of the text with the sedLinks to an external site. commands:

sed -i '' 's/sdl.h/SDL2\/SDL.h/g' {*.c,*.h} # for sdl.h

sed -i '' 's/SDL2_gfx-1.0.1\/SDL2_gfxPrimitives.h/SDL2\/SDL2_gfxPrimitives.h/g' {*.c,*.h} # for SDL2_gfxPrimitives.h

# 7. Compile the code:
    gcc wall.c formulas.c robot.c main.c -o main -lSDL2

Breaking this line down:

gcc a C/C++ compiler

wall.c formulas.c robot.c main.c the names of your C code files.

-o main gives the executable file a name robot. Otherwise the compiler calls it a.out

-lSDL2 links the SDL2 library to your code (Note: it's a lower case letter L in front of the SDL2)

8. Run it. Either double click main in Finder or in the terminal type:

./main

Terminate the simulation by closing the temrinal window