# EpionicTEWH
Updates I added: ability to display the numbers in a fragment that is redrawn continuously without the numbers flashing.
This is done through a system of storing these numbers and sending them to the fragment when the fragment is redrawn, as well as 
in the bluetooth's run() method calls. The displays which are coming from the main fragment rather than the main activity have been
tagged with [frag] by me, which you will see flash because that indicates the main fragment's oncreate
is being called continuously, but only in between the bluetooth method calls.

Things that need to be fixed:
This will take MANY ATTEMPTS to open the app without crashing or switch tabs without crashing, but it will run indefinitely
if the app is able to be opened. Most likely, a button to start data transfer after the app has loaded would be useful
so that fragments aren't being continuously created/destroyed while the app is trying to be loaded. If this does not work,
another approach could be to revise the whole android life cycle stuff, as well as a way to handle the first case of the program's 
display on loading the app. In short, the android life cycle components of the app need to be revised, as well as a way to handle the
infinite fragment creations occurring when the app tries to load.
