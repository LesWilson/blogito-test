package blogito

import static org.junit.Assert.*

import grails.test.mixin.support.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class UnderscoreCodecTests {

    void setUp() {
        // Setup logic here
		String.metaClass.encodeAsUnderscore = {->
          UnderscoreCodec.encode(delegate)
        }
		String.metaClass.decodeUnderscore = {->
          UnderscoreCodec.decode(delegate)
        }

    }

    void tearDown() {
        // Tear down logic here
    }
    void testEncode() {
      String test = "this is a test"
      assertEquals "this_is_a_test", test.encodeAsUnderscore()
    }
  
    void testDecode() {
      String test = "this_is_a_test"
      assertEquals "this is a test", test.decodeUnderscore()
    }
}
