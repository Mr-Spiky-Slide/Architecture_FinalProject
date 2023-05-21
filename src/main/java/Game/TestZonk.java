package Game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestZonk {
    ArrayList<Integer> threeOfAKindDice = new ArrayList<>();
    ArrayList<Integer> zonkDice = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        threeOfAKindDice.add(1);
        threeOfAKindDice.add(2);
        threeOfAKindDice.add(3);
        threeOfAKindDice.add(1);
        threeOfAKindDice.add(1);

        zonkDice.add(2);
        zonkDice.add(4);
        zonkDice.add(6);
        zonkDice.add(2);
    }

    @Test
    public void ZonkTest(){
        assertFalse("Three of a kind should not have zonked", Zonk.testZonk(threeOfAKindDice, threeOfAKindDice));

        assertTrue("Zonk dice should have zonked", Zonk.testZonk(zonkDice, zonkDice));
    }

    @Test
    public void ThreeOfAKindTest(){
        assertTrue("Three of a kind should be true", Zonk.threeOfAKind(threeOfAKindDice));

        assertFalse("Three of a kind should be false", Zonk.threeOfAKind(zonkDice));
    }







}


