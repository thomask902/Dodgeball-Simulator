import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TestDodgeballManager {
    
    @Test // test ensures that constructor is appending names properly
	public void testConstructorName() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		DodgeballNode temp = test.dodgerFirstNode;
		DodgeballNode temp2 = test.throwerFirstNode;
		assertTrue(temp.name.equals("Sarah"));
		assertTrue(temp2.next.next.name.equals("Justin"));
	}
	
	@Test // test ensures that each node has been constructed with a score of 0
	public void testConstructorScore() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		assertEquals(0, test.dodgerFirstNode.score);
		assertEquals(0, test.throwerFirstNode.next.next.next.score);
	}
	// test ensures that IllegalArgumentException is thrown when empty list is passed into constructor
	@Test (expected = IllegalArgumentException.class) 
	public void testConstructorEmptyList() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = new LinkedList<>();
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
	}
	
	// test ensures that IllegalArgumentException is thrown when null list is passed into constructor
	@Test (expected = IllegalArgumentException.class) 
	public void testConstructorNullList() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = null;
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
	}
	
	@Test // test ensures that constructor is not creating extra nodes when length of one of the lists is only 1
	public void testConstructorLengthOfOne() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		assertEquals(null, test.dodgerFirstNode.next);
	}
	
	
	@Test // test ensures that constructor is appending names properly even for extremely long case
	public void testConstructorNameLong() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", 
				"Jonas", "Ella", "Reese", "Claire", "Andrew", "Marshall", "Tyson",
				"Jake", "Rushon", "Gordon");
		List<String> dodgers = Arrays.asList("Sarah");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		DodgeballNode temp = test.throwerFirstNode;
		while(temp.next != null) {
			temp = temp.next;
		}
		assertTrue(temp.name.equals("Gordon"));
	}
	
	// test ensures IllegalArgumentException is thrown if dodger or thrower passed into dodge method is not in dodger list
	@Test (expected = IllegalArgumentException.class)
	public void testDodgeNotInList() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.dodge("Matt", "Bert");
	}
	
	// test ensures IllegalArgumentException is thrown if dodger or thrower passed into dodge method is null
	@Test (expected = IllegalArgumentException.class)
	public void testDodgeNullParamter() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.dodge(null, "Bert");
	}
	
	// test ensures IllegalArgumentException is thrown if dodger or thrower passed into dodge method is empty
	@Test (expected = IllegalArgumentException.class)
	public void testDodgeEmptyParamter() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.dodge("", "Bert");
	}
	
	@Test // tests that the dodge method is properly adding a point to dodger
	public void testDodgeStandardPoint() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.dodge("Matt", "Reese");
		DodgeballNode temp = test.dodgerFirstNode;
		assertEquals(1, temp.next.next.score);
	}
	
	@Test // tests that the dodge method is properly adding a point to dodger
	public void testDodgeExtremeCase() {
		List<String> throwers = Arrays.asList("Wanda");
		List<String> dodgers = Arrays.asList("Matt", "John", "Justin", "Thomas", 
				"Jonas", "Ella", "Reese", "Claire", "Andrew", "Marshall", "Tyson",
				"Jake", "Rushon", "Gordon");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.dodge("Wanda", "Gordon");
		DodgeballNode temp = test.dodgerFirstNode;
		while(temp.next != null) {
			temp = temp.next;
		}
		assertEquals(1, temp.score);
	}
	
	// test ensures IllegalArgumentException is thrown if dodger or thrower passed into hit method is not in dodger list
	@Test (expected = IllegalArgumentException.class)
	public void testHitNotInList() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.hit("tommy", "Sarah");
	}
	
	// test ensures IllegalArgumentException is thrown if dodger or thrower passed into hit method is null
	@Test (expected = IllegalArgumentException.class)
	public void testHitNullParamter() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.hit(null, "Bert");
	}
	
	// test ensures IllegalArgumentException is thrown if dodger or thrower passed into hit method is empty
	@Test (expected = IllegalArgumentException.class)
	public void testHitEmptyParamter() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.hit("", "Bert");
	}
	
	@Test // tests that the hit method is properly adding a point to thrower
	public void testHitUpdateScore() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.hit("John", "Reese");
		DodgeballNode temp = test.dodgerFirstNode;
		assertEquals(1, temp.next.next.score);
	}
	
	@Test // tests that the hit method swaps the position of the player properly when one of them is at the front of their list
	public void testHitInFront() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.hit("Matt", "Reese");
		assertTrue(test.throwerFirstNode.name.equals("Reese"));
	}
	
	@Test // tests that the hit method is properly swapping the position of the players when they are both in the middle of their lists
	public void testHitBothInMiddle() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.hit("Justin", "Ella");
		DodgeballNode temp = test.dodgerFirstNode;
		assertTrue(temp.next.name.equals("Justin"));
	}
	
	@Test // tests that the hit method swaps the position of the player properly when one of them is at the back of their list
	public void testHitInBack() {
		List<String> throwers = Arrays.asList("Matt", "John", "Justin", "Thomas", "Jonas");
		List<String> dodgers = Arrays.asList("Sarah", "Ella", "Reese", "Claire");
		DodgeballManager test = new DodgeballManager(throwers, dodgers);
		test.hit("John", "Claire");
		DodgeballNode temp = test.dodgerFirstNode;
		while(temp.next != null) {
			temp = temp.next;
		}
		assertTrue(temp.name.equals("John"));
	}
}