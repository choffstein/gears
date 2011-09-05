# gears

Gears is a library of random utility functions for Clojure.  
Instead of putting helper functions into separate libraries and bother with 
managing them all independently, we have thrown them all together here.

We've tried to keep the naming fairly consistent and obvious.  If you want a function
that has to do with vectors, then it is probably in gears.vector.  If you want a 
helper function that has to do with dates, then it is probably in gears.date.  A function
that applies across all seqs?  In gears.seq.  

You get the idea.

Besides generic helper functions, there are some pretty specific functions in here, like
an rss generator and some http middleware for ring.  These may be one day extracted to
their own libraries, but for now, they sit in gears. 

## Usage

See the marginalia generated uberdoc in the docs folder for help with a specific function.

## Other

Please feel free to fork, fix, and pull for something as minor as code style to as major as
a global-meltdown of a bug.

## License

Copyright (C) 2011 Newfound Research, LLC

Distributed under the Eclipse Public License, the same as Clojure.
