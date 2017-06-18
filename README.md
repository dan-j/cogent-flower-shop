# cogent-flower-shop
Flower Shop coding challenge for Cogent

A flower shop sells flowers in bundles, they need an application that given an order of arbitrary
number of flowers, calculate the minimum number of bundles to fulfil the order.

## Requirements

You'll need Java 8, there's a heavy reliance on the Streams API, you could easily develop the
application in an earlier version, but who in their right mind would do that nowadays?!

The project is built with [Gradle] but a Gradle Wrapper is included in the repository so you don't
necessarily need Gradle installed to use the application (although you'll need an internet
connection for the wrapper to download the binaries)

## Build & Run

To build, run:

    ./gradlew build

And to run the application (which will also run `build` if required):

    ./gradlew run

The application accepts input from `stdin`, so once you've done typing in your order, simply press
`ctrl+D` to close the input and the application will process it. The input should be in the form of:

    10 R12
    15 L09
    13 T58

Where each line is an order with quantity and product code respectively.

There is an example input file if you want to run the application without typing the information in
manually, from the root of the git repository run:

    ./gradlew run < input.txt

You should see output like the following:

    10 R12 $12.99
            1 x 10 $12.99
    15 L09 $41.90
            1 x 9 $24.95
            1 x 6 $16.95
    13 T58 $25.85
            2 x 5 $9.95
            1 x 3 $5.95
    Total cost:     $80.74


## Configuration

The cost of different flowers and their corresponding bundle prices can be found in
[default-config.json]. The future plan is to allow users to specify their own JSON file on the
command line, however for now we're stuck with these default values.

## Future work

Command line parameters to control input and output locations (so not fixed to `stdin` and
`stdout`), and also an option to specify JSON file containing the inventory details.

[Gradle]: https://gradle.org/
[default-config.json]: src/main/resources/default-config.json