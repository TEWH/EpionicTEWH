# EpionicTEWH
Code with data transfer from activity->fragment works here, with a few problems

-Bugs associated with moving from one fragment to another -- probably the result of my continual redrawing of the fragment on every iteration

-It might be possible that "MainFragVis" is true by the pressing of a button but not true in terms of it having been created now

-Program crash upon many times when it starts, probably due to receiving lots of data and continually recreating/uncreating a fragment;
solution to this problem is to have a button to start data transfer to avoid a big influx of data and fragment creation / re-creation when
the app begins.

-Program does NOT crash when it is simply open and run without changing fragments, but it DOES crash if the screen times out and goes 
to sleep. This leads me to believe it's a combination of the continual feed of bluetooth in conjunction with my redrawing the fragment
over and over again.

-Program doesn't crash as a result of the Bluetooth input being changed, etc: I can even reset the Bluetooth module mid-operation with no error.

Next steps:

-Look in to Handler? Maybe I can avoid continually redrawing the fragment

-Temporary solution to the redrawing 3 pieces of info problem could be simply sending the most recent BP/Temp reading even when it has
updated Oxygen reading, for ex; this avoids sending massive amounts of info in the form of a Parcelable at once, and this strategy
could also work for ECG in the form of sending the last few readings to maintain them on the redrawn graph

-It's possible that we could make the only background process be the flagging system, and we could separate that from anything to do with buttons. That could potentially reduce the possibility for crashes because it would provide more opportunities to structure the code differently.
