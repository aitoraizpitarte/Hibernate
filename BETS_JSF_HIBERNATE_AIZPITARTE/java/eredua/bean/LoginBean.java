package eredua.bean;

import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import gui.MainGUI;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import configuration.UtilDate;
import eredua.bean.HibernateUtil;
import eredua.bean.Erabiltzailea;
import eredua.bean.LoginGertaera;

import org.hibernate.Query;
import org.hibernate.Session;
import java.util.*;

public class LoginBean {
	private static int apostuMin = 2;

	private static ArrayList<String> erakustekoak;

	private ArrayList<String> galde;
	private Date data;
	static private FacadeBean f;

	private static ArrayList<Question> galderak;

	private static ArrayList<Event> ebentuak;
	private int kodea = 1;
	private Integer i;
	private String deskripzioa;
	private static int azkenID;
	private String selectedValue;

	public void setErakustekoak(ArrayList<String> erakustekoak) {
		this.erakustekoak = erakustekoak;
	}

	public String getDeskripzioa() {
		return deskripzioa;
	}

	public void setDeskripzioa(String deskripzioa) {
		this.deskripzioa = deskripzioa;
	}

	public static FacadeBean getF() {
		return f;
	}

	public static void setF(FacadeBean f) {
		LoginBean.f = f;
	}

	public int getKodea() {
		return kodea;
	}

	public void setKodea(int kodea) {
		this.kodea = kodea;
	}

	public Integer getI() {
		return i;
	}

	public void seti(Integer i) {
		this.i = i;
		System.out.println("pero aqui no");
		galde = new ArrayList<String>();
		for (Question s : /*select*/) {
			galde.add(s.getQuestion());
			System.out.println("galderak: " + s.getQuestion());
		}
	}

	public ArrayList<String> getGalde() {
		return galde;
	}

	public void setGalde(ArrayList<String> galde) {
		this.galde = galde;
	}

	public Question getQ() {
		return q;
	}

	public void setQ(Question q) {
		this.q = q;
	}

	private Question q;

	public Date getData() {
		return data;
	}

	public void setData(Date data1) {
		erakustekoak = new ArrayList<String>();
		galde = new ArrayList<String>();
		this.data = data1;
		for (Event s : e.createEvent /* selecta */) {
			if (s.getEventDate().equals(data1)) {
				erakustekoak.add(s.getDescription());
			}

		}

	}

	public String sortu() {
		return "ok";
	}

	public String galdera() {
		return "error";
	}

	public ArrayList<String> getErakustekoak() {
		return erakustekoak;
	}

	public List<Question> getGalderak(String s) {
		Event z=null;
		for (Event j : f.getBusinessLogic().getEvents(data)) {
			if (j.getDescription() == s) {
				i=j.getEventNumber();
				z=j;
			}
		}
		return z.getQuestions();
	}

	public String gaderaSortu() {
		if (i == null) {
			System.out.println("e=null da");
			for (Event x : getEvents()) {
				if (x.getEventDate().equals(data)) {
					i = x.getEventNumber();
					break;
				}
			}
		}
		for (Event z : getEvents()) {

			if (z.getEventNumber()==i) {
				i = z.getEventNumber();
			}
		}
		addQuestion(i,deskripzioa, 1);

		System.out.println("se ha creado la pregunta: " + deskripzioa);

		return "sortu";
	}

	public Event getEventById(Integer zenb) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("select id from Event where eventDate= :d");
		q.setParameter("date", d);
		ArrayList result=(ArrayList) q.list();
		session.getTransaction().commit();
		return result;
		}
	}
	
	public void addQuestion(Integer i2, String deskripzioa2, int j) {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();

	    Question question = new Question(i2,deskripzioa2, j);


	    session.save(question);
	    session.getTransaction().commit();
	}

	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Data aukeratua: " + event.getObject()));
	}

	public void listener(AjaxBehaviorEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Galdera:" + q.getQuestion()));
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		System.out.println("setSelectedValue");
		this.selectedValue = selectedValue;
	}

	public void actualizarE(AjaxBehaviorEvent event) {
		for (Event m : getEvents()) {
			if (m.getDescription().equals(deskripzioa)) {
				this.seti(m.getEventNumber());
				;
			}
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(""));
		System.out.println("llega aqui");
	}

	public String buelta() {
		return "hasierara";
	}

	public String ebentuetara() {
		return "spawneatu";
	}

	public String kalendariora() {
		return "kalendar";
	}

	public LoginBean() {
	}

	public static void main(String[] args) {
		ebentuak = new ArrayList<Event>();
		galderak = new ArrayList<Question>();
		LoginBean e = new LoginBean();
		Event ev2 = new Event(1, "Atlético-Athletic", UtilDate.newDate(2024, 1, 17));
		Event ev1 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(2024, 1, 17));
		Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(2024, 1, 17));
		Event ev4 = new Event(4, "Alavés-Deportivo", UtilDate.newDate(2024, 1, 17));
		Event ev5 = new Event(5, "Español-Villareal", UtilDate.newDate(2024, 1, 17));
		Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(2024, 1, 17));
		Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(2024, 1, 17));
		Event ev8 = new Event(8, "Girona-Leganés", UtilDate.newDate(2024, 1, 17));
		Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(2024, 1, 17));
		Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(2024, 1, 17));

		Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(2024, 1, 1));
		Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(2024, 1, 1));
		Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(2024, 1, 1));
		Event ev14 = new Event(14, "Alavés-Deportivo", UtilDate.newDate(2024, 1, 1));
		Event ev15 = new Event(15, "Español-Villareal", UtilDate.newDate(2024, 1, 1));
		Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(2024, 1, 1));

		Event ev17 = new Event(17, "Málaga-Valencia", UtilDate.newDate(2024, 1, 28));
		Event ev18 = new Event(18, "Girona-Leganés", UtilDate.newDate(2024, 1, 28));
		Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(2024, 1, 28));
		Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(2024, 1, 28));
		Question q1;
		Question q2;
		Question q3;
		Question q4;
		Question q5;
		Question q6;
		q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
		q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
		q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
		q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
		q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
		q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
		e.createGalderak(q1);
		e.createGalderak(q2);
		e.createGalderak(q3);
		e.createGalderak(q4);
		e.createGalderak(q5);
		e.createGalderak(q6);
		e.createEvent(ev2);
		e.createEvent(ev1);
		e.createEvent(ev3);
		e.createEvent(ev4);
		e.createEvent(ev5);
		e.createEvent(ev6);
		e.createEvent(ev7);
		e.createEvent(ev8);
		e.createEvent(ev9);
		e.createEvent(ev10);
		e.createEvent(ev11);
		e.createEvent(ev12);
		e.createEvent(ev13);
		e.createEvent(ev14);
		e.createEvent(ev15);
		e.createEvent(ev16);
		e.createEvent(ev17);
		e.createEvent(ev18);
		e.createEvent(ev19);
		e.createEvent(ev20);
		galderak.add(q1);
		galderak.add(q2);
		galderak.add(q3);
		galderak.add(q4);
		galderak.add(q5);
		galderak.add(q6);

		ebentuak.add(ev2);
		ebentuak.add(ev1);
		ebentuak.add(ev3);
		ebentuak.add(ev4);
		ebentuak.add(ev5);
		ebentuak.add(ev6);
		ebentuak.add(ev7);
		ebentuak.add(ev8);
		ebentuak.add(ev9);
		ebentuak.add(ev10);
		ebentuak.add(ev11);
		ebentuak.add(ev12);
		ebentuak.add(ev13);
		ebentuak.add(ev14);
		ebentuak.add(ev15);
		ebentuak.add(ev16);
		ebentuak.add(ev17);
		ebentuak.add(ev18);
		ebentuak.add(ev19);
		ebentuak.add(ev20);
		erakustekoak=new ArrayList<String>();
		azkenID=20;
	}
	

	private void createEvent(Event e) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.persist(e);
		session.getTransaction().commit();
	}

	private void createGalderak(Question q) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.persist(q);
		session.getTransaction().commit();
	}
	
	public ArrayList<Event> getEvents() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("select eventNumber from Event");
		ArrayList result=(ArrayList) q.list();
		session.getTransaction().commit();
		return result;
		}

	public ArrayList<Event> getEvents(Date d) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("select eventNumber from Event where eventDate= :d");
		q.setParameter("date", d);
		ArrayList result=(ArrayList) q.list();
		session.getTransaction().commit();
		return result;
		}
}