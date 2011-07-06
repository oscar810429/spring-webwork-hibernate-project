package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.Painiu;
import com.painiu.event.EventSource;
import com.painiu.core.security.PrivacyRestricted;

/**
 * <p>
 * <a href="Photo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Photo.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Photo extends BaseObject implements EventSource, PrivacyRestricted {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = 4869952473074163121L;

	public static class State extends IntegerEnum implements Serializable {

		public static final State UNDECLARED = new State(0);
	    public static final State PUBLISHED  = new State(1);
	    public static final State CANCELED   = new State(2);
	    public static final State DELETED    = new State(3);
	    public static final State BLOCKED    = new State(4);
	    
	    public static final State USER_PRODUCT 			= new State(5);
		public static final State USER_OTHER 			= new State(6);
		public static final State USER_GREEN 			= new State(7);
		public static final State USER_POPULAR 			= new State(8);
		public static final State USER_COMMENDATORY  	= new State(9);
		public static final State USER_SENIOR 			= new State(10);
		public static final State USER_CHECK_LATER 		= new State(11);
		public static final State USER_BLOCKED			= new State(12);
		
		public static final State USER_PRODUCT_UNCHECKED 		= new State(30);
		public static final State USER_OTHE_UNCHECKED 			= new State(31);
		public static final State USER_GREEN_UNCHECKED 			= new State(32);
		public static final State USER_POPULAR_UNCHECKED 		= new State(33);
		public static final State USER_COMMENDATORY_UNCHECKED  	= new State(34);
		public static final State USER_SENIOR_UNCHECKED 		= new State(35);
		public static final State USER_CHECK_LATER_UNCHECKED 	= new State(36);
		public static final State USER_BLOCKED_UNCHECKED		= new State(37);
		
	    private State(int value) {
	    	super(value);
	    }
	    
	    public static State valueOf(int value) {
	    	return (State) IntegerEnum.valueOf(State.class, value);
	    }
	}
	
	public static int OFFSET_UNCHECKED_PHOTO_FROM_USER = 30;
	public static int OFFSET_UNCHECKED_PHOTO_FROM_CHECKED = 25;
	public static int OFFSET_CHECKED_PHOTO_FROM_USER = 5;
	
	public enum Size {
    	ORIGINAL("original", -1),
    	SQUARE  ("square",   75),
    	THUMB   ("thumb",    100),
    	SMALL   ("small",    240),
    	MEDIUM  ("medium",   500),
    	BIG     ("big",      800),
    	LARGE   ("large",    1024);
    	
    	private final String name;
    	private final int size;
    	
    	Size(String name, int size) {
    		this.name = name;
    		this.size = size;
    	}
    	
    	public String getName() {
    		return name;
    	}
    	
    	public int getSize() {
    		return size;
    	}
    }
	
	//~ Instance fields ========================================================

	private String id;
	
	private String title;
	
	private String description;
	
	private Integer creativeType;
	
	private Date postedDate;
	
	private Long timestamp;
	
	private String rawTags;
	
	// original filename
	private String originalFilename;
	
	// file length
	private Integer fileLength;
	
	// whether the original file is downloadable
	private Boolean reserved = Boolean.FALSE;
	
	private PhotoAddress address;
	
	// image width
	private Integer width;
	
	// image height
	private Integer height;
	
	// license
	private License license = License.NONE;

	// visibility
	private Privacy privacy = Privacy.EVERYONE;
	
	// permission: who can comments? who can add notes/tags?
	//private PhotoPermission permission;

	// Stat. informations
	//private PhotoStat stat;

	// status
	private State state = State.PUBLISHED;
	
	// photo owner
	private User user;
	
	private String userId;
	
	private String username;
	
	// Metadata Exif... null if the image file have no exif metadata
	private Date takenDate;
	private String cameraModel;
	
	private PhotoMeta metadata;
	
	// Tags
	private Set photoTags = null;
	
	// Albums which this photo belongs to
	private Set albumPhotos = null;
	
	// Groups which this photo belongs to
	private Set groupPhotos = null;
	
	private Set favoritePhotos = null;

	private Set comments = null;
	
	private Set notes = null;
	
//	private String username = null;
//	private String fileKey = null;
	

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public boolean isPrivate() {
    	return !isPublic();
    }
    
    public boolean isPublic() {
    	return (Painiu.PRIVACY_EVERYONE & getPrivacy().value()) > 0;
    }
    
    public boolean isContactsVisible() {
    	return (Painiu.PRIVACY_CONTACTS & getPrivacy().value()) > 0;
    }
    
    public boolean isFriendsVisible() {
    	return (Painiu.PRIVACY_FRIENDS & getPrivacy().value()) > 0;
    }
	
    public boolean isFamilyVisible() {
    	return (Painiu.PRIVACY_FAMILY & getPrivacy().value()) > 0;
    }
    
    /*public boolean containsTag(String tagName) {
    	for (Iterator i = getPhotoTags().iterator(); i.hasNext();) {
			PhotoTag tag = (PhotoTag) i.next();
			if (tag.getTagName().equals(tagName)) {
				return true;
			}
		}
    	return false;
    }
    
    public boolean addPhotoTag(PhotoTag photoTag) {
    	if (photoTag == null) {
    		throw new IllegalArgumentException("Can not add NULL PhotoTag");
    	}
    	
    	for (Iterator i = getPhotoTags().iterator(); i.hasNext();) {
			PhotoTag tag = (PhotoTag) i.next();
			if (tag.getTagName().equals(photoTag.getTagName())) {
				return false;
			}
		}
    	getPhotoTags().add(photoTag);
    	return true;
    }
    
    public void removePhotoTag(PhotoTag photoTag) {
    	getPhotoTags().remove(photoTag);
    }
    
	public void addComment(Comment comment) {
		if (comment == null) {
			throw new IllegalArgumentException("Can not add NULL comment");
		}
		
		PhotoComment pc = (PhotoComment) comment;
		pc.setTarget(this);
		pc.setPhoto(this);
		if (getComments().add(pc)) {
			this.getStat().setComments(getComments().size());
		}
	}

	public void removeComment(Comment comment) {
		if (getComments().remove(comment)) {
			this.getStat().setComments(getComments().size());
		}
	}
	
	public void addNote(PhotoNote note) {
		if (note == null) {
			throw new IllegalArgumentException("can not add NULL note");
		}
		
		note.setPhoto(this);
		if (getNotes().add(note)) {
			this.getStat().setNotes(getNotes().size());
		}
	}
	
	public void removeNote(PhotoNote note) {
		if (getNotes().remove(note)) {
			this.getStat().setNotes(getNotes().size());
		}
	}*/
	
	public int getThumbWidth() {
        return getWidth(Size.THUMB);
    }
    
    public int getThumbHeight() {
        return getHeight(Size.THUMB);
    }
    
    public int getSmallWidth() {
        return getWidth(Size.SMALL);
    }
    
    public int getSmallHeight() {
        return getHeight(Size.SMALL);
    }
    
    public int getMediumWidth() {
        return getWidth(Size.MEDIUM);
    }
    
    public int getMediumHeight() {
        return getHeight(Size.MEDIUM);
    }
    
    public int getBigWidth() {
    	return getWidth(Size.BIG);
    }
    
    public int getBigHeight() {
    	return getHeight(Size.BIG);
    }
    
    public int getLargeWidth() {
    	return getWidth(Size.LARGE);
    }
    
    public int getLargeHeight() {
    	return getHeight(Size.LARGE);
    }
    
    public int getOriginalWidth() {
    	return getWidth(Size.ORIGINAL);
    }
    
    public int getOriginalHeight() {
    	return getHeight(Size.ORIGINAL);
    }

    public int getWidth(Size v) {
        int width = 0;

        int oWidth = getWidth().intValue();
        int oHeight = getHeight().intValue();

        if (v == Size.ORIGINAL) {
            return oWidth;
        }

        if (v == Size.SQUARE) {
            return v.getSize();
        }

        width = v.getSize();

        if (width > oWidth && width > oHeight) {
        	return oWidth;
        }
        
        double ratio = (double) oWidth / (double) oHeight;

        if (ratio > 1) {
            return width;
        }
		return (int) (width * ratio);
    }

    public int getHeight(Size v) {
        int height = 0;

        int oWidth = getWidth().intValue();
        int oHeight = getHeight().intValue();

        if (v == Size.ORIGINAL) {
            return oHeight;
        }

        if (v == Size.SQUARE) {
            return v.getSize();
        }

        height = v.getSize();

        if (height > oHeight && height > oWidth) {
        	return oHeight;
        }
        
        double ratio = (double) oWidth / (double) oHeight;

        if (ratio < 1) {
            return height;
        }
		return (int) (height / ratio);
    }
    
    /*public String getSmallURL() {
        return getURL(Size.SMALL);
    }

    public String getMediumURL() {
        return getURL(Size.MEDIUM);
    }

    public String getLargeURL() {
        return getURL(Size.LARGE);
    }
    
    public String getBigURL() {
    	return getURL(Size.BIG);
    }
    
    public String getOriginalURL() {
    	return getURL(Size.ORIGINAL);
    }

    public String getThumbURL() {
        return getURL(Size.THUMB);
    }

    public String getSquareURL() {
        return getURL(Size.SQUARE);
    }*/
    
    public String getSmallURL() {
        return getURL(Painiu.PHOTO_SMALL);
    }

    public String getMediumURL() {
        return getURL(Painiu.PHOTO_MEDIUM);
    }
    
    public String getLargeURL() {
    	return getURL(Painiu.PHOTO_LARGE);
    }

    public String getOriginalURL() {
    	return getURL(Painiu.PHOTO_LARGE);
    }

    public String getThumbURL() {
        return getURL(Painiu.PHOTO_THUMB);
    }

    public String getSquareURL() {
        return getURL(Painiu.PHOTO_SQUARE);
    }
    
    /*
    private static DataSource mappingDataSource;
    public static void setMappingDataSource(DataSource ds) {
    	mappingDataSource = ds;
    }
    
    private void loadYPFSMapping() {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	try {
    		conn = mappingDataSource.getConnection();
    		pstmt = conn.prepareStatement("select username, filename, secret from ypd_import_mapping where photo_id = ?");
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();
    		if (rs.next()) {
    			address.setUsername(rs.getString(1));
    			address.setFileKey(rs.getString(2));
    			address.setSecret(rs.getString(3));
    		}
    		rs.close();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		if (pstmt != null) {
    			try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    		if (conn != null) {
    			try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    	}
    	
    }
	*/

    public static boolean USE_PNFS = Painiu.getPhotoConfig().usePNFS();
    /*public String getURL(Size v) {
    	if (USE_YPFS) {
    		
    		if (address.getFileKey() == null) {
    			loadYPFSMapping();
    		}
    		
    		if (address.getFileKey() == null) {
    			return address.getURL(v.getName());
    		}
    		
    		return address.getURL(v);
    	}
    	return address.getURL(v.getName());
    }*/
    
    public String getURL(String v) {
        return address.getURL(v);
    }
    
    /**
     * TODO remove this method, when YPFS is published
     * @return
     */
    public String getMetaURL() {
    	return address.getMetaURL();
    }
    
    /*public String getTagsAsString() {
    	return TagUtils.toString(getPhotoTags());
    }*/
	
    public String getOriginalFormat() {
    	String ext = FilenameUtils.getExtension(this.getOriginalFilename());
    	if (ext != null) {
    		return ext.toLowerCase();
    	}
    	return null;
    }
    
	// Accessors =====================================================
    public Integer getCreativeType() {
		return creativeType;
	}

	public void setCreativeType(Integer creativeType) {
		this.creativeType = creativeType;
	}
	
	public void setCreativeType(int creativeType) {
		this.setCreativeType(new Integer(creativeType));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getFileLength() {
		return fileLength;
	}

	public void setFileLength(Integer fileLength) {
		this.fileLength = fileLength;
	}
	
	public void setFileLength(int fileLength) {
		setFileLength(new Integer(fileLength));
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public void setHeight(int height) {
		setHeight(new Integer(height));
	}

	public PhotoAddress getAddress() {
		/*
		if (address.fileKey == null) {
			loadYPFSMapping();
		}
		*/
		return address;
	}
	
	public void setAddress(PhotoAddress address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	/*public PhotoPermission getPermission() {
		return permission;
	}

	public void setPermission(PhotoPermission permission) {
		permission.setPhoto(this);
		this.permission = permission;
	}*/

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestemp) {
		this.timestamp = timestemp;
	}

	public String getRawTags() {
		return rawTags;
	}

	public void setRawTags(String rawTags) {
		this.rawTags = rawTags;
	}

	public Boolean getReserved() {
		return reserved;
	}

	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}

	/*public PhotoStat getStat() {
		return stat;
	}

	public void setStat(PhotoStat stat) {
		stat.setPhoto(this);
		this.stat = stat;
	}*/
	
	/**
	 * @return Returns the state.
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * @param state The state to set.
	 */
	public void setState(State state) {
		this.state = state;
	}
	
	public Set getPhotoTags() {
		if (photoTags == null) {
			photoTags = new TreeSet();
		}
		return photoTags;
	}

	public void setPhotoTags(Set photoTags) {
		this.photoTags = photoTags;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		this.setUsername(user.getUsername());
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public void setWidth(int width) {
		setWidth(new Integer(width));
	}
	
	public void setComments(Set comments) {
		this.comments = comments;
	}
	
	/*
	 * @see com.mingda.model.Commentable#getComments()
	 */
	public Set getComments() {
		if (comments == null) {
			comments = new TreeSet();
		}
		return comments;
	}
	
	/**
	 * @return Returns the license.
	 */
	public License getLicense() {
		return license;
	}

	/**
	 * @param license The license to set.
	 */
	public void setLicense(License license) {
		this.license = license;
	}

	/**
	 * @return Returns the privacy.
	 */
	public Privacy getPrivacy() {
		return privacy;
	}

	/**
	 * @param privacy The privacy to set.
	 */
	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}
	
	/**
	 * @return Returns the notes.
	 */
	public Set getNotes() {
		if (notes == null) {
			notes = new HashSet(5);
		}
		return notes;
	}
	
	/**
	 * @param notes The notes to set.
	 */
	public void setNotes(Set notes) {
		this.notes = notes;
	}

	/**
	 * @return Returns the albumPhotos.
	 */
	public Set getAlbumPhotos() {
		if (albumPhotos == null) {
			albumPhotos = new HashSet(5);
		}
		return albumPhotos;
	}

	/**
	 * @param albumPhotos The albumPhotos to set.
	 */
	public void setAlbumPhotos(Set albumPhotos) {
		this.albumPhotos = albumPhotos;
	}

	/**
	 * @return Returns the groupPhotos.
	 */
	public Set getGroupPhotos() {
		if (groupPhotos == null) {
			groupPhotos = new HashSet(5);
		}
		return groupPhotos;
	}

	/**
	 * @param groupPhotos The groupPhotos to set.
	 */
	public void setGroupPhotos(Set groupPhotos) {
		this.groupPhotos = groupPhotos;
	}

	/**
	 * @return Returns the favoritePhotos.
	 */
	public Set getFavoritePhotos() {
		if (favoritePhotos == null) {
			favoritePhotos = new HashSet();
		}
		return favoritePhotos;
	}

	/**
	 * @param favoritePhotos The favoritePhotos to set.
	 */
	public void setFavoritePhotos(Set favoritePhotos) {
		this.favoritePhotos = favoritePhotos;
	}

	/**
	 * @return Returns the cameraModel.
	 */
	public String getCameraModel() {
		return cameraModel;
	}

	/**
	 * @param cameraModel The cameraModel to set.
	 */
	public void setCameraModel(String cameraModel) {
		this.cameraModel = cameraModel;
	}

	/**
	 * @return Returns the takenDate.
	 */
	public Date getTakenDate() {
		return takenDate;
	}

	/**
	 * @param takenDate The takenDate to set.
	 */
	public void setTakenDate(Date takenDate) {
		this.takenDate = takenDate;
	}

	/**
	 * @return Returns the metadata.
	 */
	public PhotoMeta getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata The metadata to set.
	 */
	public void setMetadata(PhotoMeta metadata) {
		this.metadata = metadata;
		metadata.setPhoto(this);
	}

	/* (non-Javadoc)
	 * @see com.mingda.security.Personal#getPerson()
	 */
	public User getPerson() {
		return getUser();
	}
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Photo)) {
			return false;
		}
		Photo rhs = (Photo) object;
		return new EqualsBuilder()
				.append(this.user, rhs.user)
				.append(this.rawTags, rhs.rawTags)
				.append(this.width, rhs.width)
				.append(this.title, rhs.title)
				.append(this.postedDate, rhs.postedDate)
				.append(this.timestamp, rhs.timestamp)
				.append(this.address, rhs.address)
				.append(this.state, rhs.state)
				.append(this.privacy, rhs.privacy)
				.append(this.license, rhs.license)
				.append(this.creativeType, rhs.creativeType)
				.append(this.fileLength, rhs.fileLength)
				.append(this.height, rhs.height)
				//.append(this.stat, rhs.stat)
				.append(this.description, rhs.description)
				//.append(this.permission, rhs.permission)
				.append(this.originalFilename, rhs.originalFilename)
				.append(this.reserved, rhs.reserved)
				.append(this.takenDate, rhs.takenDate)
				.append(this.cameraModel, rhs.cameraModel)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1154975431, 1752119917)
				.append(this.user)
				.append(this.rawTags)
				.append(this.width)
				.append(this.title)
				.append(this.postedDate)
				.append(this.timestamp)
				.append(this.address)
				.append(this.state)
				.append(this.privacy)
				.append(this.license)
				.append(this.creativeType)
				.append(this.fileLength)
				.append(this.height)
				//.append(this.stat)
				.append(this.description)
				//.append(this.permission)
				.append(this.originalFilename)
				.append(this.reserved)
				.append(this.takenDate)
				.append(this.cameraModel)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id)
				.append("title", this.title).toString();
	}
}
