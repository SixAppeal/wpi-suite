/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

/**
 * @author nathan
 * @author santiago
 * @author jill
 */
public class Activity {

		Member member;
		String comment;
		
		/**
		 *  Constructor for an activity
		 * @param member member that made the comment
		 * @param comment comment that the member made
		 */
		public Activity(Member member, String comment){
			this.member = member;
			this.comment = comment;
		}
}
