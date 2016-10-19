# Quickstart

## Framework build

1. Clone project `git clone git@github.com:neva-dev/javarel-framework.git`
2. Enter cloned directory `cd javarel-framework`
3. Install it locally `gradle clean publishToMavenLocal` (until it being released)

## Application build

1. Clone project `git clone git@github.com:neva-dev/javarel-quickstart.git`
2. Enter cloned directory `cd javarel-quickstart`
3. Build application using command `gradle createOsgiContainer --no-daemon`.
4. Run script _run.sh_ from directory _build/osgiContainer_.
5. Web application is available at address _http://localhost:6661_ and can be debugged using port _16661_.

## IDE setup

### IntelliJ

1. Create new run configuration named 'Create container' of type 'Gradle'.
    * Select root project
    * Tasks: `clean createOsgiContainer`
    * Script parameters: `--no-daemon`
2. Install _Bash_ plugin.
3. Create run configuration named 'Run container' of type 'Bash'.
    * Select working directory to _build/osgiContainer_ 
    * Fill script with 'run.sh'. 
    * As before action add 'Run other run configuration', select 'Create container'
4. Select 'Run container' in top right corner of IDE and run it for each code change :)
5. Install _Twig_ plugin.
6. Open settings tab _File Types". Associate '*.peb' files with 'Twig'.