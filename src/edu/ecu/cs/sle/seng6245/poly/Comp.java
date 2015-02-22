package edu.ecu.cs.sle.seng6245.poly;

import java.util.Iterator;

public class Comp {
	public static Poly diff(Poly p) throws NullPointerException {
		// EFFECTS: If p is null throws NullPointerException else
		// returns the poly obtained by differentiating p.
		Poly q = new Poly();
		Iterator<Integer> g = p.terms();
		while (g.hasNext()) {
			int exp = g.next().intValue();
			if (exp == 0) continue;
			q = q.add(new Poly(exp*p.coeff(exp), exp-1));
		}
		return q;
	}
	
	public static void printPrimes(int m) {
		// MODIFIES: System.out
		// EFFECTS: Prints all the primes less than or equal to m on System.out
		Iterator<Integer> g = Num.allPrimes();
		while (true) {
			int p = g.next().intValue();
			if (p > m) return;
			System.out.println("The next prime is: " + p);
		}
	}	
}
