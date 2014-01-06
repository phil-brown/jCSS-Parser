CSS Stream Parser for Java
==========================

## Introduction

This is a *Stream Parser* for reading *Cascading Style-Sheets* (CSS) using *Java*. Used correctly,
this can provide a fast and efficient way for parsing CSS. It is also designed to be scalable for new
versions of CSS, in that adding additional support for At-Rules is straight-forward, adding new syntax is
relatively straight-forward, and all of the current features are supported (as of January, 2014).

I am currently using this project in the [droidQuery](http://bit.ly/droidquery) project, to use CSS to
manipulate the *Android* UI. Apart from that, this library has not been extensively tested. If you find
any issues, please report them [here](https://github.com/phil-brown/jCSS-Parser/issues).

## Usage

The simplest way to include *jCSS-Parser* in your project is to build using *ant*, then include the *jar*
in your build path. The simplest way to parse a CSS file is using the following code:

    try
    {
        //First get the input stream. For example, from a file
        InputStream is = new FileInputStream(new File("/path/to/my/CSS/main.css"));

        //Then parse        
        CSSParser parser = new CSSParser(is, new DefaultCSSHandler());
        parser.parse();
    }
    catch (Throwable t)
    {
        t.printStackTrace();
    }
    
For more help, see [Tester.java](https://github.com/phil-brown/jCSS-Parser/blob/master/CSS%20Parser/src/self/philbrown/cssparser/Tester.java).

## License

Finally, jCSS-parser is available under the [Apache License 2.0](https://github.com/phil-brown/jCSS-Parser/blob/master/LICENSE).

