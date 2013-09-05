# clojure.mn

The web site source for [clojure.mn](http://clojure.mn/), the Minnesota Clojure group.

## Development:
After changing things, you *must* run cljsbuild to re-generate
target/cljsbuild-main.js. This file is checked in so the nodejs
buildpack can use it.

## LaTeX code for creating the lambda logo:
    $$
    \mbox{\Huge $\lambda$}_\texttt{mn}
    $$

## Thanks to:
* [Ben Peirce](http://bpeirce.me) for originally maintaining and
  creating this site
* Brian Maddy for changing it to use nodejs so it's faster on Heroku

## To Do:
* Add Google Analytics
