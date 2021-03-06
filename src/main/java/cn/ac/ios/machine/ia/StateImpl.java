package cn.ac.ios.machine.ia;


import java.util.BitSet;


public class StateImpl implements State {

	protected final int index;
	protected final InterfaceAutomaton IA;
	protected final Transition[] trans;
	
	public StateImpl(InterfaceAutomaton automaton, int index){
		this.index = index;
		this.IA = automaton;
		//The last transition is for delta
		this.trans = new Transition[automaton.getInAPs().size() + automaton.getOutAPs().size() + 1];
		for(int i = 0; i < this.trans.length; i++){
			this.trans[i] = new TransImpl(-1, IA);
		}
	}
	
	@Override
	public InterfaceAutomaton getIA() {
		return IA;
	}

	@Override
	public Boolean isInitial(int state) {
		assert state <= IA.getStateSize();
		if(IA.getInitial().getIndex() == state){
			return true;
		}
		return false;
	}

	@Override
	public int getIndex() {
		return this.index;
	}


	@Override
	public void addTransition(int letter, int state) {
		trans[letter] = new TransImpl(letter, IA);
		trans[letter].setSuccessor(state);

	}

	@Override
	public Boolean isEnable(int letter) {
		for(int i = 0; i < this.getSuccessors(letter).length(); i++){
			if(this.getSuccessors(letter).get(i)){
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean isInputEnable() {
		for(int i = 0; i < IA.getInAPs().size(); i++){
			if(this.getSuccessors(i).size() == 0){
				return false;
			}
		}
		return true;
	}

	@Override
	public BitSet getSuccessors(int letter) {
		return this.trans[letter].getSuccessors();
	}

	@Override
	public Boolean isQuiescent() {
		for(int i = this.getIA().getInApSize(); 
				i < this.getIA().getTotalApSize(); 
				i++){
			if(this.isEnable(i)){
				return false;
			}
		}
		return true;
	}

	@Override
	public int getTotalApSize() {
		return this.getIA().getTotalApSize();
	}

	@Override
	public int getInApSize() {
		return this.getIA().getInApSize();
	}

	@Override
	public int getOutApSize() {
		return this.getIA().getOutAPs().size();
	}

	@Override
	public void rmTransition(int letter) {
		this.trans[letter] = new TransImpl(-1, IA);
	}

	@Override
	public int getSuccessor(int letter) {
		System.out.println("only supported on DIA");
		return -1;
	}

	@Override
	public Boolean isOutputDetermined() {
		Boolean flag = false;
		for(int letter = this.getInApSize(); letter < this.getTotalApSize()+1; letter++){
			if(this.getSuccessors(letter).cardinality() != 0){
				if(flag){
					return false;
				} else {
					flag = true;
				}
			}
		}
		return true;
	}

}
