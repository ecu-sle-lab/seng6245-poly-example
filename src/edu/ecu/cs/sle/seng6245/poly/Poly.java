package edu.ecu.cs.sle.seng6245.poly;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Poly implements Cloneable {
	// OVERVIEW: Polys are immutable polynomials with integer coefficients
	//           A typical Poly is c0 + c1x + ...
	private int[] trms;
	private int deg;
	
	// constructors
	public Poly() {
		// EFFECTS: Initializes this to be the zero polynomial
		trms = new int[1];
		deg = 0;
	}
	
	public Poly(int c, int n) throws NegativeExponentException {
		// EFFECTS: If n < 0 throws NegativeExponentException else
		// initializes this to be the Poly cx^n
		if (n < 0) {
			throw new NegativeExponentException("Poly(int,int) constructor");
		}
		if (c == 0) {
			trms = new int[1];
			deg = 0;
			return;
		}
		trms = new int[n+1];
		for (int i = 0; i < n; ++i) {
			trms[i] = 0;
		}
		trms[n] = c;
		deg = n;
	}
	
	private Poly(int n) {
		trms = new int[n+1];
		deg = n;
	}
	
	// methods
	public int degree() {
		// EFFECTS: Returns the degree of this, i.e., the largest exponent
		// with a non-zero coefficient. Returns 0 if this is the zero Poly.
		return deg;
	}
	
	public int coeff(int d) {
		// EFFECTS: Returns the coefficient of the term of this whose exponent is d.
		if (d < 0 || d > deg) {
			return 0;
		} else {
			return trms[d];
		}
	}
	
	public Poly add(Poly q) throws NullPointerException {
		// EFFECTS: If q is null throws NullPointerException else
		// returns the Poly this + q.
		Poly la, sm;
		if (deg > q.deg) {
			la = this;
			sm = q;
		} else {
			la = q;
			sm = this;
		}
		int newdeg = la.deg; // new degree is the larger degree
		if (deg == q.deg) { // unless there are trailing zeros
			for (int k = deg; k > 0; k--) {
				if (trms[k] + q.trms[k] != 0) {
					break;
				} else {
					newdeg--;
				}
			}
		}
		Poly r = new Poly(newdeg);
		int i;
		for (i = 0; i <= sm.deg && i <= newdeg; ++i) {
			r.trms[i] = sm.trms[i] + la.trms[i];
		}
		for (int j = i; j <= newdeg; ++j) {
			r.trms[j] = la.trms[j];
		}
		return r;
	}
	
	public Poly mul(Poly q) throws NullPointerException {
		// EFFECTS: If q is null throws NullPointerException else
		// returns the Poly this * q.
		if ((q.deg == 0 && q.trms[0] == 0) || (deg == 0 && trms[0] == 0)) {
			return new Poly();
		}
		Poly r = new Poly(deg+q.deg);
		r.trms[deg+q.deg] = 0; // prepare to compute coeffs
		for (int i = 0; i <= deg; i++) {
			for (int j = 0; j <= q.deg; j++) {
				r.trms[i+j] = r.trms[i+j] + trms[i]*q.trms[j];
			}
		}
		return r;
	}
	
	public Poly sub(Poly q) throws NullPointerException {
		// EFFECTS: If q is null throws NullPointerException else
		// returns the Poly this - q.
		return add(q.minus());
	}
	
	public Poly minus() {
		// EFFECTS: Returns the Poly -this.
		Poly r = new Poly(deg);
		for (int i = 0; i < deg; ++i) {
			r.trms[i] = -trms[i];
		}
		return r;
	}
	
	public boolean equals(Poly q) {
		if (q == null || deg != q.deg) {
			return false;
		}
		for (int i = 0; i <= deg; i++) {
			if (trms[i] != q.trms[i]) {
				return false;
			}
		}
		return true;
	}
	
	public boolean equals(Object z) {
		if (!(z instanceof Poly)) return false;
		return equals((Poly) z);
	}
	
	public boolean repOk() {
		if (trms == null || deg != trms.length - 1 || trms.length == 0) {
			return false;
		}
		if (deg == 0) return true;
		return trms[deg] != 0;
	}
	
	public Iterator<Integer> terms() {
		// EFFECTS: Returns a generator that will produce exponents
		// of nonzero terms of this (as Integers) up to the degree,
		// in order of increasing exponent.
		return new PolyGen(this);
	}
	
	private static class PolyGen implements Iterator<Integer> {
		private Poly p; // the Poly being iterated
		private int n; // the next term to consider
		
		PolyGen(Poly it) {
			// REQUIRES: it != null;
			p = it;
			if (p.trms[0] == 0) n = 1; else n = 0;
		}
		
		public boolean hasNext() {
			return n <= p.deg;
		}

		public Integer next() throws NoSuchElementException {
			for (int e = n; e <= p.deg; e++) {
				if (p.trms[e] != 0) {
					n = e + 1;
					return e;
				}
			}
			throw new NoSuchElementException("Poly.terms");
		}
	} // end PolyGen
}	
