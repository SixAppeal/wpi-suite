/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

/**
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 */
public class Activity {

		Member member;
		String comment;
		
		/**
		 *  Constructor for an activity
		 * @param member member that made the comment
		 * @param comment comment that the member made
		 */
		Activity(Member member, String comment){
			this.member = member;
			this.comment = comment;
		}

		
		
		//Getters and Setters
		/**
		 * @return the member
		 */
		public Member getMember() {
			return member;
		}

		/**
		 * @param member the member to set
		 */
		public void setMember(Member member) {
			this.member = member;
		}

		/**
		 * @return the comment
		 */
		public String getComment() {
			return comment;
		}

		/**
		 * @param comment the comment to set
		 */
		public void setComment(String comment) {
			this.comment = comment;
		}
}
