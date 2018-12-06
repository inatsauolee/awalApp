package bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Groupe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idGroupe;
	
	@Column(unique = true)
	private String username;
	
	@Column
	private String name;
	
	@Column
	private Date date;
	
	@ManyToMany
	@JoinTable(name="goupe_contacts",
	 joinColumns=@JoinColumn(name="idGroupe"),
	 inverseJoinColumns=@JoinColumn(name="idUser")
	)
	private List<Contact> contacts = new ArrayList<Contact>();
	
	@Column
	private byte[] image;

	@Column
	private String base64Encoded;
	
	public Groupe() {
		this.username = "";
		this.name = "";
		this.date = new Date();
		this.image=null;
		this.base64Encoded="";
	}
	
	public Groupe(String username, String name, String members, Date date, byte[] img, String bs) {
		this.username = username;
		this.name = name;
		this.date = date;
		this.image=img;
		this.base64Encoded=bs;
	}
	
	public int getIdGroupe() {
		return idGroupe;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	
	public void addContact(Contact c){
		if(!this.contacts.contains(c)) this.contacts.add(c);
	}
	
	public void removeContact(Contact c){
		if(this.contacts.contains(c)) this.contacts.remove(c);
	}
	
	public boolean isInGroupe(Contact c){
		return this.contacts.contains(c); 
	}

	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] data) {
		this.image = data;
	}
	
	public String getBase64image() {
		return this.base64Encoded;
	}
	
	public void setBase64image(String base64Encoded) {
		this.base64Encoded = base64Encoded;
	}
	
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

}
