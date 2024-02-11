# Tower-defense-game
In the game, the player must be able to place and 
upgrade towers strategically on a map to prevent enemies from reaching the wizard’s house. 
Mana is the currency of the game used to buy and upgrade towers. Each time an enemy reaches the wizard’s house,
mana must be expended to banish it. 

The goal is to survive all waves of enemies without letting the
wizard’s mana run down to 0.


# Borad\
The board consists of a grid of tiles 20x20. Each tile is 32x32 pixels, so the total is 640x640 pixels.
However, there are 40 pixels at the top reserved for information such as the timer indicator for when the
current wave will end or the next one will start, and the wizard’s current mana. There is also 120 pixels on
the right sidebar for the gameplay action buttons and information about upgrade cost. The window size is
therefore 760x680.

# Config\
The config file is in located in config.json
in the root directory of the project (the
same directory as build.gradle and the
src folder). Use the simple json library to
read it. Sample config and level files are
provided in the scaffold.

# Waves\
The configuration file contains a list of waves in the “waves” attribute. Each wave occurs in the sequence
it is provided there. During a wave, monsters spawn randomly on paths leading outside the map. A wave
is defined in the config file with the following properties:

• pre_wave_pause: Number of seconds to wait before the wave begins. This begins counting
after the duration of the previous wave has ended (or in the case of the first wave, immediately
when the game starts).

• duration: Seconds during which this wave spawns monsters. The monsters will spawn
randomly in equal intervals. For example, if the duration is 5 seconds, and there are a total of 25
monsters in this wave, there should be 5/25 = 0.2 seconds between each monster spawn, or at 60
frames per second (FPS), 12 frames in between monster spawns.

monsters: A list of monster types to spawn during this wave. The wave may contain multiple
different types of monsters, and will choose a type at random when spawning each one (as long
as there is quantity of that type remaining for this wave). The total number of monsters in the
wave is the sum of quantities for all types provided in this list.

# Monster\
Monsters initially spawn outside the map on paths that join with the edge of the map. The spawn point is
determined randomly out of all available locations. They travel on paths towards the Wizard’s house. The
below configuration options define a monster type (as part of a wave):

• type: the sprite image to use for the monster (png file extension)

• hp: amount of max hit points (initial health) the monster has

• speed: how fast the monster’s movement is in pixels per frame

• armour: percentage multiplier to damage received by this monster

• mana_gained_on_kill: How much mana the wizard gains when this monster is killed

# Towers\
Towers can be placed by the player in empty grass tiles, by first activating the tower action (either clicking
on the T button in the right sidebar, or pressing the key ‘t’), then clicking on the space they want the
tower to be placed. The following configuration options are relevant for towers:

• tower_cost: The cost in mana to place a tower. Depending on if upgrades are selected, the
initial tower cost may have +20 (one upgrade), +40 (two upgrades) or +60 (three upgrades).

• initial_tower_range: The radius in pixels from the center of this tower within which it is
able to target and shoot at monsters.

• initial_tower_firing_speed: Fireballs per second rate that this tower shoots at.

• initial_tower_damage: Amount of hitpoints deducted from a monster when a fireball from
this tower hits it. If the monster’s health reaches 0, then it dies.

# Fireball\
Fireballs are created by towers to shoot an enemy currently within their range. The choice of which
enemy to shoot is arbitrary. Fireballs move at a speed of 5 pixels per frame. A fireball originates at the
centre of the tower and targets the centre of the enemy it was fired at. When it reaches the centre of that
enemy, it damages the enemy.

# Wizard\
The Wizard’s house is the destination for monsters. When a monster reaches the wizard’s house, the
monster is ‘banished’ – mana equal to the monster’s remaining HP is expended in order to make the
monster respawn and navigate the path again.

The Wizard’s house should be rendered
on top of other tiles and any monsters.

# Mana\
The wizard’s mana is visible in an aqua bar at the top of the screen, as shown in images previously. There
is a mana cap – additional mana is not gained if the cap is reached. The initial values of these are set by
the configuration options initial_mana and initial_mana_cap. A small trickle of mana is also
gained as time passes, specified in the configuration attribute
initial_mana_gained_per_second.

Mana Pool Spell

The mana pool spell is a special action that can be done at any time to increase the wizard’s mana cap and
add a multiplier to mana gained from monster kills and the mana trickle. The following configuration
options are relevant:

• mana_pool_spell_initial_cost: The initial cost of
the spell, in mana.

• mana_pool_spell_cost_increase_per_use: How
much the spell’s cost should increase after each use.

• mana_pool_spell_cap_multiplier: The multiplier
to use for increasing the mana cap when the spell is cast (its
effect is multiplied when activated multiple times).

• mana_pool_spell_mana_gained_multiplier:
The multiplier to use for increasing mana gained from killing
monsters and the mana trickle when the spell is cast. Its
effect is added if the spell is cast multiple times (eg. 1.1 is
+10% bonus to mana gained, which would become +20% if
the spell is cast twice, or +30% if cast 3 times).


# Gameplay action\
The gameplay actions available to the player are listed in the right sidebar. When the player hovers their
mouse over one of these options (when unselected), it should turn grey. When selected, it should be
highlighted yellow. If clicked while selected, it will be unselected. Multiple options may be selected at
once. Each action has a corresponding key that also may be pressed to toggle the option. The actions are:

• Fast forward (FF) – key: f. The game will run at 2x speed – meaning anything that takes time is
sped up, including monster movement, wave timers, tower firing speed, and mana increment.
Unselecting this option will resume normal speed.

• Pause (P) – key: p. The game will be paused and in a frozen state. Unselecting this option resumes
the game. While paused, the player is still able to build and upgrade towers or cast the mana pool
spell.

• Build tower (T) – key: t. When selected, allows the player to click a grass tile on the map to place a
tower there (provided they have enough mana). If they don’t click a grass tile, or don’t have
enough mana, this selection has no effect. The cost should be displayed when the mouse hovers
over this option. The cost is only deducted when the tower is placed though, not when this option
is selected.

• Upgrades (U1, U2, U3) – keys: 1,2,3. Allows the player to upgrade towers. May also be combined
with build tower.

• Mana pool spell (M) – key: m. When selected, will activate the Mana Pool spell (see above on
page 6) and then immediately 

# Win and lose condition\
The game ends in a win for the player when all waves are over and all monsters have been defeated.
Display “YOU WIN” on the screen.

The game ends in a loss when a monster reaches the wizard’s house with a banishment cost (current HP)
greater than the wizard’s current mana balance. Display “YOU LOST” on the screen, along with an option
to restart the game by pressing the ‘r’ key. Restarting should cause everything in the game to be reset to
its original state.


