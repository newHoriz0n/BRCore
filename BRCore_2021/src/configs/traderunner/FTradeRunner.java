package configs.traderunner;

import javax.swing.JFrame;

public class FTradeRunner extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FTradeRunner(TradeRunner tr) {
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		
		add(new PTradeRunnerPanel(tr));
		
		addKeyListener(tr);
		
	}

}
