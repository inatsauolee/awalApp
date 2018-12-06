package bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Parametres{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idParametres;

	@Column
	private String textCryptingType;

	@Column
	private String imageCryptingType;

	@Column
	private String status;
	
	public Parametres() {
		this.textCryptingType = "NONE";
		this.imageCryptingType = "NONE";
		this.status = "Disponible";
	}
	
	public Parametres(String textCryptingType, String imageCryptingType, String status) {
		this.textCryptingType = textCryptingType;
		this.imageCryptingType = imageCryptingType;
		this.status = status;
	}

	public String getTextCryptingType() {
		return textCryptingType;
	}

	public void setTextCryptingType(String textCryptingType) {
		this.textCryptingType = textCryptingType;
	}

	public String getImageCryptingType() {
		return imageCryptingType;
	}

	public void setImageCryptingType(String imageCryptingType) {
		this.imageCryptingType = imageCryptingType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Parametres [idParametres=" + idParametres + ", textCryptingType=" + textCryptingType
				+ ", imageCryptingType=" + imageCryptingType + ", status=" + status + "]";
	}
	
}
