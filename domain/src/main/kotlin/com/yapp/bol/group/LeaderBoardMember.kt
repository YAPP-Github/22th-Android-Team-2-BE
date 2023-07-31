package com.yapp.bol.group

import com.yapp.bol.group.member.Member

data class LeaderBoardMember(
    val member: Member,
    val score: Int?,
    val matchCount: Int?,
    val isChangeRecent: Boolean,
)
