package cn.ac.ios.machine.ia;


import java.util.BitSet;


public class StateImpl implements State {

	protected final int index;
	protected final InterfaceAutomaton IA;
	protected final Transition[] trans;
	
	public StateImpl(InterfaceAutomaton automaton, int index){
		this.index = index;
		this.IA = automaton;
		this.trans = new Transition[automaton.getInAPs().size() + automaton.getOutAPs().size()];
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
		trans[letter] = new TransImpl(letter);
		trans[letter].setSuccessor(state);

	}

	@Override
	public Boolean isEnable(int letter) {
		
		return null;
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

}