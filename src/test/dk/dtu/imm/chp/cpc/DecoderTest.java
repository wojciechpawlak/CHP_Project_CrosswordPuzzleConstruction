package dk.dtu.imm.chp.cpc;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DecoderTest {

	private Decoder decoder;
	
	@Before
	public void setUp() {
		decoder = Decoder.getInstance();
	}

	
	@Test
	public void test() {
		decoder.decode("test01.CPC");
		
		assertEquals(3, decoder.getAlphabetSize());
		assertEquals(11, decoder.getNoOfStrings());
		assertEquals(3, decoder.getPuzzleSize());

		assertEquals(3, decoder.getAlphabet().size());
		assertEquals(Character.valueOf('a'), decoder.getAlphabet().get(0)); // here Character
		
		char[][] testEntries = decoder.getEntries();
		
		assertEquals('#', testEntries[0][0]); // here char - should not be like that
		
		assertEquals(11, decoder.getStrings().size());
		assertEquals("aa", decoder.getStrings().get(0));
		
	}

	@After
	public void cleanUp() {

	}

}
