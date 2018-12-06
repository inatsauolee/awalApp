package bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idUser;

	@Column(unique = true)
	private String username;

	@Column
	private String name;

	@Column
	private String password;
	
	@Column
	private String email;

	@Column
	private Date date;
	
	@OneToOne(targetEntity=Parametres.class, fetch = FetchType.EAGER, cascade =  {
            CascadeType.ALL
    })
	private Parametres param;

	@Column
	private byte[] image;

	@Column
	private String base64Encoded;
	
	@ManyToMany(targetEntity=Contact.class, fetch = FetchType.EAGER,
	        cascade =
	        {
	                CascadeType.DETACH,
	                CascadeType.MERGE,
	                CascadeType.REFRESH,
	                CascadeType.PERSIST
	        })
	@JoinTable(name="contact_cyrcles",
	 joinColumns= {@JoinColumn(name="idUser")},
	 inverseJoinColumns={@JoinColumn(name="idCyrcle")}
	)
    @NotFound(action=NotFoundAction.IGNORE)
	private List<Contact> contacts = new ArrayList<Contact>();
	
	
	@ManyToMany(targetEntity=Groupe.class, fetch = FetchType.EAGER, cascade =  {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
	@JoinTable(name="goupe_contacts",
	 joinColumns=@JoinColumn(name="idUser"),
	 inverseJoinColumns=@JoinColumn(name="idGroupe")
	)
    @NotFound(action=NotFoundAction.IGNORE)
	private List<Groupe> groupes = new ArrayList<Groupe>();

	public Contact() {
		this.username = "";
		this.name = "";
		this.password = "";
		this.email = "";
		this.date = new Date();
		this.image=null;
		this.base64Encoded="";
	}

	public Contact(String username, String name, String password, String email, Date date, String bs, byte[] img) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		this.date = date;
		this.image=img;
		this.base64Encoded=bs;
	}

	public int getIdUser() {
		return idUser;
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
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
	
	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(List<Contact> cs) {
		this.contacts = cs;
	}
	
	public void addCyrcle(Contact c){
		if(!this.contacts.contains(c)) this.contacts.add(c);
	}
	
	public void removeCyrcle(Contact c){
		if(this.contacts.contains(c)) this.contacts.remove(c);
	}
	
	public boolean isCyrcle(Contact c){
		return this.contacts.contains(c); 
	}
	
	public Parametres getParam() {
		return param;
	}

	public void setParam(Parametres param) {
		this.param = param;
	}

	public List<Groupe> getGroupes() {
		return groupes;
	}

	public void addGroupe(Groupe g){
		this.groupes.add(g);
	}
	
	public void removeGroupe(Groupe g){
		this.groupes.remove(g);
	}
	
	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}

	@Override
	public String toString() {
		return "Contact [idUser=" + idUser + ", username=" + username + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", date=" + date + "]";
	}

}
