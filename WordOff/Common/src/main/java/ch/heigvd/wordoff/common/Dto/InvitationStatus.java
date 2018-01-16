/*
 * File: InvitationStatus.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto;

public enum InvitationStatus {
    ORIGIN, // The target is the creator of the mode.
    WAITING, // The target has not yet responded to the invitation.
    ACCEPT, // The target has accepted the invitation, he belongs to the mode.
    DENY // The target has rejected the invitation, he does not belong to the mode.
}
