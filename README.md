# clojure.mn

The web site source for [clojure.mn](http://clojure.mn/), the Minnesota Clojure group.

## Development:

### Recompiling changes in `src`
```
lein cljsbuild once
```
This will rewrite `target/cljsbuild-main.js`

### Running locally
Run the command in the `Procfile`

## LaTeX code for creating the lambda logo:
    $$
    \mbox{\Huge $\lambda$}_\texttt{mn}
    $$

## Thanks to:
* [Ben Peirce](http://bpeirce.me) for originally maintaining and
  creating this site
* Brian Maddy for changing it to use nodejs so it's faster on Heroku
  and maintaining the site

## To Do:
* Add Google Analytics
