package com.yapp.bol.game.member

import com.yapp.bol.InvalidMatchMemberException
import com.yapp.bol.game.GameId
import com.yapp.bol.game.member.GameMember.Companion.MINIMUM_GAME_MEMBER_SIZE
import com.yapp.bol.group.GroupId
import com.yapp.bol.group.member.MemberId
import com.yapp.bol.match.dto.CreateMatchDto
import com.yapp.bol.season.SeasonService
import org.springframework.stereotype.Service

@Service
class GameMemberServiceImpl(
    private val gameMemberQueryRepository: GameMemberQueryRepository,
    private val gameMemberCommandRepository: GameMemberCommandRepository,
    private val seasonService: SeasonService
) : GameMemberService {
    override fun processScore(createMatchDto: CreateMatchDto): List<GameMember> {
        val gameId = createMatchDto.gameId
        val group = createMatchDto.groupId
        val memberDtos = createMatchDto.createMatchMemberDtos
        val memberCount = createMatchDto.createMatchMemberDtos.size

        if (!validateGameMemberSize(memberCount)) {
            throw InvalidMatchMemberException
        }

        val gameMembers = memberDtos.map { createMatchMemberDto ->
            val gameMember = getOrCreateGameMember(
                memberId = createMatchMemberDto.memberId,
                gameId = gameId,
                groupId = group
            )

            gameMember.processMatch(
                rank = createMatchMemberDto.ranking,
                memberCount = memberCount
            )
        }

        return gameMembers
    }

    private fun getOrCreateGameMember(memberId: MemberId, gameId: GameId, groupId: GroupId): GameMember {
        val gameMember = gameMemberQueryRepository.findGameMember(memberId = memberId, gameId = gameId, groupId = groupId)

        if (gameMember != null) {
            return gameMember
        }

        val season = seasonService.getOrCreateSeason(groupId = groupId)

        return gameMemberCommandRepository.createGameMember(
            GameMember.of(
                gameId = gameId,
                memberId = memberId,
                season = season
            ),
            groupId = groupId
        )
    }

    private fun validateGameMemberSize(gameMemberSize: Int): Boolean =
        gameMemberSize >= MINIMUM_GAME_MEMBER_SIZE
}
