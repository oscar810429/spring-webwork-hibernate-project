package com.painiu.core.dao;

import java.util.Date;
import java.util.List;

//import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.model.User;
import com.painiu.core.model.UserCookie;
//import com.painiu.core.model.UserEmailConfig;
//import com.painiu.core.model.UserPreference;
import com.painiu.core.search.Result;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="UserDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:zolazhou@gmail.com">Zola Zhou</a>
 */
public interface UserDAO extends DAO {

    /**
     * Gets users information based on userId.
     * @param userId
     * @return user populated user object
     */
    public User getUser(String userId);

    /**
     * Gets user information based on username.
     * @param username the current username
     * @return user populated user object
     */
    public User getUserByUsername(String username);
    
    public User getUserByNickname(String nickname);

    /**
     * Get user information based on email.
     * @param email
     * @return user populated user object
     */
    public User getUserByEmail(String email);
    
//    public User getUserByOpenID(String openid);

    public Result findUsers(String keyword, int start, int limit);

    public Result getRecommendUsers(Date fromDate, Date toDate, int start, int limit);
    
    public Result getRecommendUsers(Date fromDate, Date toDate, String orderby, int start, int limit);

	public List getRecommendUsers(Date fromDate, Date toDate, int limit);
	
	public List getRandRecommendUsers(int limit);

    /**
     * Get user preference by userId
     * @param userId
     * @return
     */
    /*public UserPreference getUserPreference(String userId) throws ObjectRetrievalFailureException;

    public void saveUserPreference(UserPreference userPreference);

    public UserEmailConfig getUserEmailConfig(User user);

    public UserEmailConfig getUserEmailConfig(String email);

    public void saveUserEmailConfig(UserEmailConfig config);*/


    /**
     * Saves a user's information
     * @param user the object to be saved
     */
    public void saveUser(User user);

    /**
     * Removes a user from the database by id
     * @param id the user's id
     */
    public void removeUser(String id);

    /**
     * Gets a userCookie object from the database,
     * based on username and password
     * @param cookie with username and password
     */
    public UserCookie getUserCookie(UserCookie cookie);

    /**
     * Saves a userCookie object to the database
     * @param cookie
     */
    public void saveUserCookie(UserCookie cookie);

    public void removeUserCookie(UserCookie cookie);
    
    /**
     * Removes all cookies for a specified userId
     * @param email
     */
    public void removeUserCookies(String userId);
    
    public Result getUserByAddress(String kind, String key, int start, int limit);
    
    /**
     * The user's last_login_date must after param afterDate
     * @param afterDate
     * @param beforeDate
     * @param limit
     * @return
     */
    public Result findActiveUsers(Date afterDate, Date beforeDate, int start, int limit, List userRanks);
    
    public Result findUploadUsers(Date afterDate, Date beforeDate, int start, int limit, List userRanks);
    
    public void activate(User user);
    public void demotion(User user);
    
    public Result findCommercialUsers(Boolean isCommercial, int start, int limit);

}
