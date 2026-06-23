package de.visualdigits.kaisstream.data.model.aisstreamio.common

/**
 * Marker interface for message wrappers we are interested in:
 *
 * - position reports
 * - static data reports
 *
 * all others are omitted:
 * - base station reports
 * - binary reports
 * - interrogation reports
 * - unknown reports
 */
interface AisDataWrapper  : IAisMessageWrapper
