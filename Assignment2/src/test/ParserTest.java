package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import io.Parser;

public class ParserTest {
  Parser parse = new Parser();

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void whiteSpaceTest() {
    // Parser should treat any number of whitespaces as a single whitespace
    // except when considering a string.
    String[] expected = {"echo", "\"         f     s \""};
    Assert.assertArrayEquals(
        "Parse must ignore extra whitespaces between arguments", expected,
        parse.parse("     echo     \"         f     s \"    "));
    Assert.assertArrayEquals("Parse must keep extra whitespaces within strings",
        expected, parse.parse("echo \"         f     s \""));
  }

  @Test
  public void stringTest() {
    /*
     * Parser should take strings as an individual parameter. If the string
     * entered is invalid then return String[] tokens = {"~FailedParsing~"}
     */
    String[] expected = {"~FailedParsing~"};
    Assert.assertArrayEquals("Parser must return error when unclosed string",
        expected, parse.parse("\""));
    Assert.assertArrayEquals("Parser must return error when unclosed string",
        expected, parse.parse("echo \"aa "));
    Assert.assertArrayEquals("Parser must return error when unclosed string",
        expected, parse.parse("echo aa\" "));
    Assert.assertArrayEquals("Parser must return error when unclosed string",
        expected, parse.parse("echo\" "));
    Assert.assertArrayEquals(
        "Parser must return error when string is not formatted correctly",
        expected, parse.parse("echo -\"fdf\"a"));
  }

  @Test
  public void commandTest() {
    /*
     * Parser should correctly separate a command line into correct arguments
     */
    String[] expected = {"ls"};
    Assert.assertArrayEquals("Parser should return just {\"ls\"}", expected,
        parse.parse("ls"));
    String[] expected2 = {"cat", "hi", "ds", "d", "s", "c"};
    Assert.assertArrayEquals("Parser must break command into correct tokens",
        expected2, parse.parse("cat hi ds d s c"));
    String[] expected3 =
        {"search", "dir1", "/dir1/dir2", "-type", "d", "-name", "\"dir4\""};
    Assert.assertArrayEquals("Parser must break command into correct tokens",
        expected3,
        parse.parse("search dir1 /dir1/dir2 -type d -name \"dir4\""));
  }



}