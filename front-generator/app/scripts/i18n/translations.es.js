'use strict';
define([], function() {

	return {
		// Language
		'Lang.code': 'es',

		// Register form error messages
		'Email': 'Invalid email address',
		'NotEmpty.registerForm.email': 'Invalid email address',
		'Pattern.registerForm.username': 'Username must contain only alphanumeric characters without space',
		'Size.registerForm.username': 'Username length must be between 4 and 100 characters',
		'Size.registerForm.password': 'Password length must be between 6 and 100 characters',
		'MatchingPasswords.registerForm.repeatPassword': 'Passwords must match',
		'UsernameNotRepeated.registerForm.username': 'Username already registered',
		'EmailNotRepeated.registerForm.email': 'Email already registered',

		// Log in error messages
		'login.error.message': 'Error, invalid username or password',

		// Create comment form error messages
		'Size.createCommentForm.content': 'Message length must be between 1 and 1000 characters',

		// Create group form error messages
		'Size.createGroupForm.name': 'Group name length must be between 4 and 32 characters',
		'Size.createGroupForm.description': 'Group description length must be between 6 and 1000 characters',
		'GroupnameNotRepeated.createGroupForm.name': 'Group already exists',
		'createGroupFirst.message': 'You must join or create a group to create a post.',
		'Pattern.createGroupForm.name': 'Group name must contain only alphanumeric characters without spaces',

		// Create post form error messages
		'Size.createPostForm.title': 'Title length must be between 4 and 60 characters',
		'Size.createPostForm.content': 'Content length must be between 6 and 1000 characters',
		'ValidImageFormat.createPostForm.file': 'invalid format, supported formats are jpg and png',
		'image.upload.failed.message': 'image upload failed',

		// Button messages
		'register.button.message': 'Register',
		'login.button.message': 'Login',
		'dropdown.button.all.message': 'All Posts',
		'dropdown.button.myfeed.message': 'My Feed',
		'dropdown.button.myGroups.message': 'My Groups',
		'dropdown.button.groups.message': 'All Groups',
		'dropdown.button.recommendedGroups.message': 'Recommended Groups',
		'dropdown.button.groups.title': 'GROUPS',
		'dropdown.button.other.title': 'OTHER',
		'dropdown.sort.button.title': 'SORT',
		'dropdown.sort.button.new.message': 'New',
		'dropdown.sort.button.top.message': 'Top',
		'dropdown.sort.button.controversial.message': 'Controversial',
		'createPost.button.message': 'CREATE POST',
		'createGroup.button.message': 'CREATE GROUP',
		'myProfile.message': 'My Profile',
		'settings.message': 'Settings',
		'logOut.button.message': 'Log Out',
		'filter.button.lastHour.message': 'Last hour',
		'filter.button.lastDay.message': 'Last day',
		'filter.button.lastWeek.message': 'Last week',
		'filter.button.lastMonth.message': 'Last month',
		'filter.button.lastYear.message': 'Last year',
		'filter.button.all.message': 'All',
		'more.button.message': 'MORE',
		'createGroupConfirmation.button.message': 'Create',
		'cancelGroupCreation.button.message': 'Cancel',
		'createPostConfirmation.button.message': 'Create',
		'cancelPostCreation.button.message': 'Cancel',
		'joinGroup.button.message': 'JOIN GROUP',
		'leaveGroup.button.message': 'LEAVE GROUP',
		'loginSubmit.button.message': 'Submit',
		'registerSubmit.button.message': 'Submit',
		'createCommentConfirmation.button.message': 'Comment',
		'postConfirmDelete.delete.button.message': 'Delete',
		'postConfirmDelete.cancel.button.message': 'Cancel',
		'commentConfirmDelete.delete.button.message': 'Delete',
		'commentConfirmDelete.cancel.button.message': 'Cancel',
		'comments.reply.button.message': 'REPLY',
		'comments.moreReply.button.message': 'Reply',
		'comments.moreReplies.button.message': 'Replies',
		'groupConfirmDelete.delete.button.message': 'Delete',
		'groupConfirmDelete.cancel.button.message': 'Cancel',
		'search.button.message': 'Search',

		// Title messages
		'error.title': 'Error',
		'homePage.title': 'Home Page',
		'createGroup.title': 'Create Group',
		'groupName.title': 'Group Name',
		'groupDescription.title': 'Description',
		'createPost.title': 'Create Post',
		'postTitleField.title': 'Title',
		'selectGroupOnPostCreate.title': 'Select a Group:',
		'selectImageOnPostCreate.title': 'Select an image (.jpg, .png) to upload (max. 5MB):',
		'postContent.title': 'Content',
		'postConfirmDelete.title': 'Are you sure you want to delete this post?',
		'commentConfirmDelete.title': 'Are you sure you want to delete this comment?',
		'groupConfirmDelete.title': 'Are you sure you want to delete this group?',
		'login.title': 'Login',
		'loginUsernameField.title': 'Username',
		'loginPasswordField.title': 'Password',
		'loginRememberMe.title': 'Remember me',
		'postTabTitle.title': 'Post',
		'postCommentsTitle.title': 'Comments',
		'profile.title': 'Profile',
		'profileActivity.title': 'Recent Activity',
		'profilePosts.title': 'Posts',
		'profileComments.title': 'Comments',
		'profileUpvotes.title': 'Upvotes',
		'register.title': 'Register',
		'registerEmailAddress.title': 'Email address',
		'registerUsername.title': 'Username',
		'registerPassword.title': 'Password',
		'registerConfirmPassword.title': 'Confirm Password',
		'verifyAccount.title': 'Verify Account',
		'verifiedAccount.title': 'Verified Account',
		'errorLinkAccount.title': 'Wrong Link',
		'groups.title': 'All groups',
		'myGroups.title': 'My groups',
		'groupsSearchResult.title': 'Group search results',
		'recommendedGroups.title': 'Recommended groups',

		// Informative messages
		'noPosts.made.signedOut.message': 'No posts were made. Log in and create a new one by clicking CREATE POST.',
		'noPosts.made.signedIn.message': 'No posts were made. Create a new one by clicking CREATE POST.',
		'noGroups.made.signedOut.message': 'No groups were created. Sign in and create a new one by clicking CREATE GROUP.',
		'noGroups.made.signedIn.message': 'No groups were created. Create a new one by clicking CREATE GROUP.',
		'noGroups.toRecommend.message': 'No groups to recommend. You might be subscribed to all groups or no groups were created.',
		'noGroups.found.message': 'No groups found for your search. Make sure to write the name correctly.',
		'noGroups.joined.message': 'No groups were joined. Search a group to join or create a new one by clicking CREATE GROUP.',
		'postedIn.message': 'posted in',
		'commentedIn.message': 'commented in',
		'comments.message': 'Comments',
		'comments.suggestion': 'Login to leave a comment.',
		'comments.votes': 'Votes',
		'upvotes.message': 'Upvotes',
		'posts.votes': 'Votes',
		'footer.message': 'Pawddit® is a registered trademark. Authors: Matías Heimann, Lóránt Mikolás, Diego Bruno and Johnathan Katan.',
		'groupMember.message': 'member',
		'groupMembers.message': 'members',
		'groupMembers.owner.message': 'Owned by',
		'groupMembers.owner.lower.message': 'owned by',
		'groups.memberWhoShareAGroup': 'Member who shares a group with you',
		'groups.membersWhoShareAGroup': 'Members who share a group with you',
		'userDoesNotHaveComments.message': '{{profile.username}} doesn\u2019t have any comments.',
		'userDoesNotHaveUpvotes.message': '{{profile.username}} doesn\u2019t have any upvotes.',
		'userDoesNotHavePosts.message': '{{profile.username}} doesn\u2019t have any posts.',
		'weWillNeverShareYourEmail.message': 'We\u2019ll never share your email with anyone else.',
		'verifyAccount.message': 'Please make sure to check your email and follow the instructions to verify your account.',
		'verifiedAccount.message': 'Your account was verified succesfully.',
		'groupConfirmDelete.delete.message': 'Delete',
		'profileActivity.message': 'Here you can see the last 5 posts and the last 5 comments of this user.',
		'time.filterBy.message': 'Filter by:',

		// Placeholders
		'groupName.placeholder': 'Group Name',
		'postTitle.placeholder': 'Post title',
		'loginUsername.placeholder': 'Enter username',
		'loginPassword.placeholder': 'Password',
		'registerEmailAddress.placeholder': 'Enter email',
		'registerUsername.placeholder': 'Enter username',
		'registerPassword.placeholder': 'Password',
		'registerConfirmPassword.placeholder': 'Repeat password',
		'addComment.placeholder': 'Add public comment here...',
		'searchGroups.placeholder': 'Search groups...',

		// Errors
		'errorUserNotFound.message': 'User not found.',
		'errorGroupNotFound.message': 'Group not found.',
		'errorPostNotFound.message': 'Post not found.',
		'errorCommentNotFound.message': 'Comment not found.',
		'errorVerificationTokenNotFound.message': 'Confirmation link not valid.',
		'errorImageNotFound.message': 'Image not found.',
		'errorNoPermissions.message': 'No permissions.',
		'errorLinkAccount.message': 'Ups! This is not a valid link.',

		// Meta descriptions
		'meta.description.confirmedaccount.message': 'Account confirmation was successful.',
		'meta.description.creategroup.message': 'You can create a group with a name and description.',
		'meta.description.createpost.message': 'You can create a new post with a title and content (including images).',
		'meta.description.error.message': 'An error has occurred.',
		'meta.description.feed.message': 'Pawddit posts feed.',
		'meta.description.group.message': 'Pawddit group feed.',
		'meta.description.login.message': 'Here you can login to your account.',
		'meta.description.post.message': 'Post information and comments.',
		'meta.description.profile.message': 'Profile with recent activity.',
		'meta.description.register.message': 'Here you can register a new account.',
		'meta.description.verifyaccount.message': 'Please verify you account.',
		'meta.description.myGroups.message': 'A list of all the groups joined.',
		'meta.description.groups.message': 'A list of all the groups.',
		'meta.description.recommendedGroups.message': 'A list of recommended groups.',
		'meta.description.groupsSearchResult.message': 'Group search results.',

		// Change profile image modal
		'user.modal.close.message': 'Close',
		'user.modal.changeImageConfirmation.message': 'Confirm',
		'user.modal.changeImage.message': 'Change profile picture',
		'confirmUrl': 'Click here',

		// Change profile image modal error messages
		'ValidImageFormat.changeProfilePictureForm.file': 'invalid format, supported formats are jpg and png'
	};
});
