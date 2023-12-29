package eredua.bean;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class FacadeBean {
	private static BLFacade facadeInterface;

	private FacadeBean() {
		try {
			facadeInterface = new BLFacadeImplementation(new DataAccess());
		} catch (Exception e) {
			System.out.println("FacadeBean: negozioaren logika sortzean errorea: " + e.getMessage());
		}
	}

	public BLFacade getBusinessLogic() {
		return facadeInterface;
	}

	public void createQuestion(Event e, String q, int i) throws EventFinished, QuestionAlreadyExist {
		facadeInterface.createQuestion(e, q, i);
		
	}
	
	
}