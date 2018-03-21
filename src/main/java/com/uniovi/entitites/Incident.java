package com.uniovi.entitites;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Incident {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String description;

	@Enumerated(EnumType.STRING)
	private IncidentStates state;
	private Location location;
	private List<String> tags;
	private List<String> multimedia;
	private Map<String, String> property_value;
	private List<String> comments;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "agent_id")
	private Agent agent;

	@OneToOne
	@JoinColumn(name = "notification_id")
	private Notification notification;
	
	private static String[] keyWordsDanger= {"temperature","fire","flood","windy","wonded","attack",
			"robbery","dead"}; 

	public Incident() {

	}

	public Incident(Long id, String name, String description, IncidentStates state, Location location, List<String> tags,
			List<String> multimedia, Map<String, String> property_value) {
		this();
		this.name = name;
		this.description = description;
		this.state = state;
		this.location = location;
		this.tags = tags;
		this.multimedia = multimedia;
		this.property_value = property_value;
	}

	public Incident(Long id, String name, String description, IncidentStates state, Location location, List<String> tags,
			List<String> multimedia, Map<String, String> property_value, List<String> comments) {
		this(id, name, description, state, location, tags, multimedia, property_value);
		this.comments = comments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public IncidentStates getState() {
		return state;
	}

	public void setState(IncidentStates state) {
		this.state = state;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(List<String> multimedia) {
		this.multimedia = multimedia;
	}

	public Map<String, String> getProperty_value() {
		return property_value;
	}

	public void setProperty_value(Map<String, String> property_value) {
		this.property_value = property_value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public static String[] getKeyWordsDanger() {
		return keyWordsDanger;
	}

	public static void setKeyWordsDanger(String[] keyWordsDanger) {
		Incident.keyWordsDanger = keyWordsDanger;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Incident other = (Incident) obj;
		if (id != other.id)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public boolean isDangerous() {
		if(!property_value.isEmpty()) {
			for (Map.Entry<String, String> entry : property_value.entrySet()) {
				if(Arrays.asList(keyWordsDanger).contains(entry.getKey())) {
					if(checkDanger(entry.getKey(),entry.getValue())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean checkDanger(String key, String value) {
		if(key.equals("temperature")) {
			double temp = Double.parseDouble(value);
			if(temp > 40.0 || temp < 10.0) {
				return true;
			}
		}else if(key.equals("fire")) {
			if(value.equals("yes")) {
				return true;
			}
		}else if(key.equals("flood")) {
			if(value.equals("yes")) {
				return true;
			}
		}else if(key.equals("windy")) {
			double speed = Double.parseDouble(value);
			if(speed > 30.0) {
				return true;
			}
		}else if(key.equals("wounded")) {
			int wounded = Integer.parseInt(value);
			if(wounded > 0) {
				return true;
			}
		}else if(key.equals("dead")) {
			int dead = Integer.parseInt(value);
			if(dead > 0) {
				return true;
			}
		}else if(key.equals("attack")) {
			if(value.equals("yes")) {
				return true;
			}
		}else if(key.equals("robbery")) {
			if(value.equals("yes")) {
				return true;
			}
		}
		return false;
	}

}