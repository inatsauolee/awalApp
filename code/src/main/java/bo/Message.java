package bo;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idMessage;
	
    @Column
	private String text;
    
    @Column
	private Integer idDest;
    
    @Column
	private Integer idEmet;
    
    @Column
	private Date date;
    
    @Column
	private boolean priv;
    
    @Column
	private String cryptingType;
    
    @Column
    private String cryptingKey;
    
    @Column
    private String cryptingKey2;

    @Column
    private String cryptingKey3;


    public Message() {
		this.text = "Nothing";
		this.idDest = 0;
		this.idEmet = 0;
		this.date = new Date();
		this.priv = true;
		this.cryptingType="NONE";
		this.cryptingKey=BigInteger.ZERO.toString();
		this.cryptingKey2=BigInteger.ZERO.toString();
		this.cryptingKey3=BigInteger.ZERO.toString();
	}

	public Message(String text, int idEmet, int idDest, Date date, boolean priv, String type, BigInteger key, BigInteger key2, BigInteger key3) {
		this.text = text;
		this.idDest = idDest;
		this.idEmet = idEmet;
		this.date = date;
		this.priv = priv;
		this.cryptingType=type;
		this.cryptingKey=key.toString();
		this.cryptingKey2=key2.toString();
		this.cryptingKey3=key3.toString();
	}
	
	public int getIdMessage() {
		return idMessage;
	}
	public void setIdMessage(int id) {
		this.idMessage = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getIdDest() {
		return idDest;
	}
	public void setIdDest(int idDest) {
		this.idDest = idDest;
	}
	public int getIdEmet() {
		return idEmet;
	}
	public void setIdEmet(int idEmet) {
		this.idEmet = idEmet;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isPriv() {
		return priv;
	}
	public void setPriv(boolean priv) {
		this.priv = priv;
	}
	
	public String getCryptingType() {
		return cryptingType;
	}

	public void setCryptingType(String cryptingType) {
		this.cryptingType = cryptingType;
	}

	public String getKey() {
		return cryptingKey;
	}

	public void setKey(String cryptingKey) {
		this.cryptingKey = cryptingKey;
	}

	public String getCryptingKey2() {
		return cryptingKey2;
	}

	public void setCryptingKey2(String cryptingKey2) {
		this.cryptingKey2 = cryptingKey2;
	}

	public String getCryptingKey3() {
		return cryptingKey3;
	}

	public void setCryptingKey3(String cryptingKey3) {
		this.cryptingKey3 = cryptingKey3;
	}


}
