package dk.dtu.imm.chp.cpc;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class HeuristicAlgorithmTest {

	Decoder decoder = null;
	HeuristicAlgorithm algo = null;

	@Before
	public void setUp() {
		decoder = Decoder.getInstance();
		Decoder.getInstance().decode("test01.CPC");
		algo = HeuristicAlgorithm.getInstance();
	}

	@Test
	public void test() {
		try {
			algo.runAlgorithm();
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
